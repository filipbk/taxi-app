package com.taxi.taxi.service;

import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;
import com.taxi.taxi.dto.RideDto;
import com.taxi.taxi.model.*;
import com.taxi.taxi.repository.CustomerRepository;
import com.taxi.taxi.repository.DailyInfoRepository;
import com.taxi.taxi.repository.DriverRepository;
import com.taxi.taxi.repository.RideRepository;
import com.taxi.taxi.util.GoogleMapsApiConnector;
import com.taxi.taxi.util.PriceCalculator;
import com.taxi.taxi.util.TimeDistanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RideService {
    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private DailyInfoRepository dailyInfoRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private GoogleMapsApiConnector googleMapsApiConnector;

    public RideDto orderATaxi(Customer customer, Long workingDriverId) {
        ArrayList<RideStatus> rideStatus = new ArrayList<>();
        Collections.addAll(rideStatus, RideStatus.NEW, RideStatus.REJECTED);
        
        Ride ride = rideRepository.findByCustomerAndRideStatusIn(customer, rideStatus)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("Nie moższe obecnie zamówić tej taksówki {0}", workingDriverId)));

        DailyInfo dailyInfo = dailyInfoRepository.findById(workingDriverId)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("Kierowca niedostepny {0}", workingDriverId)));

        Driver driver = dailyInfo.getDriver();

        if(driver.getDriverStatus() != DriverStatus.WORK) {
            throw new IllegalArgumentException(MessageFormat.format("Nie moższe obecnie zamówić tej taksówki {0}", workingDriverId));
        }

        LatLng origins = ride.getStartPoint();
        LatLng destinationPoint = ride.getDestinationPoint();

        DistanceMatrix matrix = googleMapsApiConnector.getDistanceMatrix(origins, destinationPoint);
        PriceCalculator priceCalculator = new PriceCalculator(dailyInfo.getCar().getPrice(), matrix);
        TimeDistanceInfo timeDistanceInfo = priceCalculator.calculateApproximatePrice();
        ride.setDistance(timeDistanceInfo.getApproxDistance());
        ride.setPrice(timeDistanceInfo.getApproxPrice());

        driver.setDriverStatus(DriverStatus.REQUEST);
        driverRepository.save(driver);
        ride.setDailyInfo(dailyInfo);
        ride.setRideStatus(RideStatus.NEW);
        rideRepository.save(ride);
        customer.setCustomerStatus(CustomerStatus.WAITING_FOR_DRIVER_ACCEPT);
        customerRepository.save(customer);

        return new RideDto(ride);
    }

    public RideDto getWaitingOrders(Driver driver) {
        //TODO dodać odległość od klienta, długość trasy i cenę
        if(driver.getDriverStatus() != DriverStatus.REQUEST) {
            throw new IllegalArgumentException("Nie masz oczekujących zgłoszeń");
        }

        Ride ride = rideRepository.findByDailyInfoDriverAndRideStatus(driver, RideStatus.NEW)
                .orElseThrow(() -> new IllegalArgumentException("Nie masz oczekujących zgłoszeń"));

        LatLng origins = ride.getStartPoint();
        LatLng destinationPoint = ride.getDestinationPoint();

        DailyInfo dailyInfo = dailyInfoRepository.findByDriverAndEndTimeIsNull(driver)
                .orElseThrow(() -> new IllegalArgumentException("Nie masz oczekujących zgłoszeń"));

        DistanceMatrix matrix = googleMapsApiConnector.getDistanceMatrix(origins, destinationPoint);
        PriceCalculator priceCalculator = new PriceCalculator(dailyInfo.getCar().getPrice(), matrix);
        TimeDistanceInfo timeDistanceInfo = priceCalculator.calculateApproximatePrice();
        RideDto rideDto = new RideDto(ride);
        rideDto.setDistance(timeDistanceInfo.getApproxDistance());
        rideDto.setPrice(timeDistanceInfo.getApproxPrice());


        return new RideDto(ride);
    }

    public RideDto getCurrentOrder(Driver driver) {
        ArrayList<RideStatus> rideStatus = new ArrayList<>();
        Collections.addAll(rideStatus, RideStatus.ACCEPTED, RideStatus.IN_PROGRESS);

        Ride ride = rideRepository.findByDailyInfoDriverAndRideStatusIn(driver, rideStatus)
                .orElseThrow(() -> new IllegalArgumentException("Nie masz aktywnych zgłoszeń"));

        LatLng origins = ride.getStartPoint();
        LatLng destinationPoint = ride.getDestinationPoint();

        DailyInfo dailyInfo = dailyInfoRepository.findByDriverAndEndTimeIsNull(driver)
                .orElseThrow(() -> new IllegalArgumentException("Nie masz aktywnych zgłoszeń"));

        DistanceMatrix matrix = googleMapsApiConnector.getDistanceMatrix(origins, destinationPoint);
        PriceCalculator priceCalculator = new PriceCalculator(dailyInfo.getCar().getPrice(), matrix);
        TimeDistanceInfo timeDistanceInfo = priceCalculator.calculateApproximatePrice();
        RideDto rideDto = new RideDto(ride);
        rideDto.setDistance(timeDistanceInfo.getApproxDistance());
        rideDto.setPrice(timeDistanceInfo.getApproxPrice());


        return rideDto;
    }

    public RideDto getCurrentOrder(Customer customer) {
        ArrayList<RideStatus> rideStatus = new ArrayList<>();
        Collections.addAll(rideStatus, RideStatus.NEW, RideStatus.IN_PROGRESS, RideStatus.REJECTED, RideStatus.ACCEPTED);

        Ride ride = rideRepository.findByCustomerAndRideStatusIn(customer, rideStatus)
                .orElseThrow(() -> new IllegalArgumentException("Nie masz aktywnych zgłoszeń"));


        return new RideDto(ride);
    }

    public RideDto rejectOrder(Driver driver) {
        Ride ride = rideRepository.findByDailyInfoDriverAndRideStatus(driver, RideStatus.NEW)
                .orElseThrow(() -> new IllegalArgumentException("Nie masz oczekujących zgłoszeń"));

        ride.setRideStatus(RideStatus.REJECTED);
        ride.setDailyInfo(null);
        rideRepository.save(ride);

        driver.setDriverStatus(DriverStatus.WORK);
        driverRepository.save(driver);

        Customer customer = ride.getCustomer();
        customer.setCustomerStatus(CustomerStatus.DRIVER_REJECTED);
        customerRepository.save(customer);

        return new RideDto(ride);
    }

    public RideDto acceptOrder(Driver driver) {
        Ride ride = rideRepository.findByDailyInfoDriverAndRideStatus(driver, RideStatus.NEW)
                .orElseThrow(() -> new IllegalArgumentException("Nie masz oczekujących zgłoszeń"));

        ride.setRideStatus(RideStatus.ACCEPTED);
        rideRepository.save(ride);

        driver.setDriverStatus(DriverStatus.NAVIGATION);
        driverRepository.save(driver);

        Customer customer = ride.getCustomer();
        customer.setCustomerStatus(CustomerStatus.WAITING);
        customerRepository.save(customer);



        LatLng origins = ride.getStartPoint();
        LatLng destinationPoint = ride.getDestinationPoint();

        DailyInfo dailyInfo = dailyInfoRepository.findByDriverAndEndTimeIsNull(driver)
                .orElseThrow(() -> new IllegalArgumentException("Nie możesz wykonać tej operacji"));

        DistanceMatrix matrix = googleMapsApiConnector.getDistanceMatrix(origins, destinationPoint);
        PriceCalculator priceCalculator = new PriceCalculator(dailyInfo.getCar().getPrice(), matrix);
        TimeDistanceInfo timeDistanceInfo = priceCalculator.calculateApproximatePrice();
        ride.setDistance(timeDistanceInfo.getApproxDistance());
        ride.setPrice(timeDistanceInfo.getApproxPrice());
        rideRepository.save(ride);

        return new RideDto(ride);
    }

    public RideDto cancelOrder(Customer customer) {
        ArrayList<RideStatus> rideStatus = new ArrayList<>();
        Collections.addAll(rideStatus, RideStatus.NEW, RideStatus.REJECTED);

        Ride ride = rideRepository.findByCustomerAndRideStatusIn(customer, rideStatus)
                .orElseThrow(() -> new IllegalArgumentException("Operacja niedozwolona"));

        if(ride.getDailyInfo() != null) {
            Driver driver = ride.getDailyInfo().getDriver();
            driver.setDriverStatus(DriverStatus.WORK);
            driverRepository.save(driver);
        }

        customer.setCustomerStatus(CustomerStatus.FREE);
        customerRepository.save(customer);

        rideRepository.delete(ride);

        return new RideDto(ride);
    }

    public RideDto driverArrived(Driver driver) {
        Ride ride = rideRepository.findByDailyInfoDriverAndRideStatus(driver, RideStatus.ACCEPTED)
                .orElseThrow(() -> new IllegalArgumentException("Nie rozpocząłeś żadnego przejazdu"));


        Customer customer = ride.getCustomer();
        customer.setCustomerStatus(CustomerStatus.DRIVER_ARRIVED);
        customerRepository.save(customer);

        return new RideDto(ride);
    }

    public RideDto startRide(Driver driver) {
        Ride ride = rideRepository.findByDailyInfoDriverAndRideStatus(driver, RideStatus.ACCEPTED)
                .orElseThrow(() -> new IllegalArgumentException("Nie rozpocząłeś żadnego przejazdu"));


        Customer customer = ride.getCustomer();
        customer.setCustomerStatus(CustomerStatus.RIDE);
        customerRepository.save(customer);

        ride.setStartTime(new Date());
        ride.setRideStatus(RideStatus.IN_PROGRESS);
        rideRepository.save(ride);

        driver.setDriverStatus(DriverStatus.DRIVE);
        driverRepository.save(driver);

        return new RideDto(ride);
    }

    public RideDto endRide(Driver driver) {
        Ride ride = rideRepository.findByDailyInfoDriverAndRideStatus(driver, RideStatus.IN_PROGRESS)
                .orElseThrow(() -> new IllegalArgumentException("Nie rozpocząłeś żadnego przejazdu"));


        Customer customer = ride.getCustomer();
        customer.setCustomerStatus(CustomerStatus.FREE);
        customerRepository.save(customer);

        ride.setEndTime(new Date());
        ride.setRideStatus(RideStatus.FINISHED);
        rideRepository.save(ride);

        driver.setDriverStatus(DriverStatus.WORK);
        driverRepository.save(driver);

        return new RideDto(ride);
    }

    public List<RideDto> getRidesHistory(Customer customer) {
        List<Ride> rides = rideRepository.findAllByCustomer(customer);
        List<RideDto> rideDtos = rides
                .stream()
                .map(ride -> new RideDto(ride))
                .collect(Collectors.toList());

        return rideDtos;
    }

    public List<RideDto> getRidesHistory(Driver driver) {
        List<Ride> rides = rideRepository.findAllByDailyInfoDriver(driver);
        List<RideDto> rideDtos = rides
                .stream()
                .map(ride -> new RideDto(ride))
                .collect(Collectors.toList());

        return rideDtos;
    }

    public List<RideDto> getRidesHistory(Company company) {
        List<Ride> rides = rideRepository.findAllByDailyInfoCompany(company);
        List<RideDto> rideDtos = rides
                .stream()
                .map(ride -> new RideDto(ride))
                .collect(Collectors.toList());

        return rideDtos;
    }
}
