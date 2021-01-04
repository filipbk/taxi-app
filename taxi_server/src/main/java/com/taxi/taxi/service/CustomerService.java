package com.taxi.taxi.service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.taxi.taxi.dto.AvailableDriversDto;
import com.taxi.taxi.dto.DailyInfoDto;
import com.taxi.taxi.model.*;
import com.taxi.taxi.repository.*;
import com.taxi.taxi.request.AvailableDriversRequest;
import com.taxi.taxi.util.GoogleMapsApiConnector;
import com.taxi.taxi.util.PriceCalculator;
import com.taxi.taxi.util.TimeDistanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DailyInfoRepository dailyInfoRepository;

    @Autowired
    private GoogleMapsApiConnector googleMapsApiConnector;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CompanyRepository companyRepository;


    public Customer getCustomerFromDb(Long customerId) {
        return customerRepository
                .findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("There is no customer with id {0}", customerId)));
    }

    public AvailableDriversDto getFreeDrivers(AvailableDriversRequest availableDriversRequest, Customer customer) {
        List<DailyInfo> freeDrivers =  dailyInfoRepository
                .findFreeDriversByCity(availableDriversRequest.getCity(), DriverStatus.WORK);

        List<DailyInfo> freeCompanyDrivers = dailyInfoRepository
                .findFreeDriversByCompanyCity(availableDriversRequest.getCity(), DriverStatus.WORK);

        freeDrivers.addAll(freeCompanyDrivers);

        List<Price> prices = freeDrivers
                .stream()
                .map(dailyInfo -> dailyInfo.getCar().getPrice())
                .collect(Collectors.toList());


        LatLng origins = new LatLng(availableDriversRequest.getSrcLat(), availableDriversRequest.getSrcLng());
        LatLng destinationPoint = new LatLng(availableDriversRequest.getDstLat(), availableDriversRequest.getDstLng());
        LatLng[] destinations = freeDrivers
                .stream()
                .map(DailyInfo::getPosition)
                .toArray(LatLng[]::new);

        DistanceMatrix matrix = googleMapsApiConnector.getDistanceMatrix(origins, destinations);
        DistanceMatrix route = googleMapsApiConnector.getDistanceMatrix(origins, destinationPoint);

        PriceCalculator priceCalculator = new PriceCalculator(prices, matrix, freeDrivers, route);
        ArrayList<TimeDistanceInfo> timeDistanceInfos = priceCalculator.calculateApproximatePrices();

        List<DailyInfoDto> result = freeDrivers
                .stream()
                .map(DailyInfoDto::new)
                .collect(Collectors.toList());

        for(int i = 0; i < result.size(); i++) {
            result.get(i).setTimeDistanceInfo(timeDistanceInfos.get(i));
        }

        customer.setCustomerStatus(CustomerStatus.RIDE_ORDER_MODE);
        customerRepository.save(customer);

        ArrayList<RideStatus> rideStatus = new ArrayList<>();
        Collections.addAll(rideStatus, RideStatus.NEW, RideStatus.REJECTED);

        Ride ride = rideRepository.findByCustomerAndRideStatusIn(customer, rideStatus).orElse(new Ride(customer, origins, destinationPoint, availableDriversRequest.getCity(), availableDriversRequest.getSrc(), availableDriversRequest.getDst(),
                route.rows[0].elements[0].distance.humanReadable));
        ride.setRideStatus(RideStatus.NEW);
        ride.setStartPoint(origins);
        ride.setDestinationPoint(destinationPoint);
        rideRepository.save(ride);

        return new AvailableDriversDto(result, availableDriversRequest, route);
    }

    public List<String> getCities() {
        List<String> cities = driverRepository
                .findAll()
                .stream()
                .map(driver -> driver.getCity())
                .collect(Collectors.toList());

        List<String> companyCities = companyRepository
                .findAll()
                .stream()
                .map(company -> company.getCity())
                .collect(Collectors.toList());

        cities.addAll(companyCities);
        cities = new ArrayList<>(new HashSet<>(cities));

        return cities;
    }
}
