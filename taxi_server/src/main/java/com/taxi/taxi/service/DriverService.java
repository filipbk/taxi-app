package com.taxi.taxi.service;

import com.google.maps.model.LatLng;
import com.taxi.taxi.dto.AvailableCarsDto;
import com.taxi.taxi.dto.CarDto;
import com.taxi.taxi.dto.CompanyDto;
import com.taxi.taxi.model.*;
import com.taxi.taxi.request.DailyInfoRequest;
import com.taxi.taxi.request.LocationUpdateRequest;
import com.taxi.taxi.repository.CarRepository;
import com.taxi.taxi.repository.DailyInfoRepository;
import com.taxi.taxi.repository.DriverCompanyRequestRepository;
import com.taxi.taxi.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private DailyInfoRepository dailyInfoRepository;

    @Autowired
    private DriverCompanyRequestRepository driverCompanyRequestRepository;

    public Driver getDriverFromDb(Long driverId) {
        return driverRepository
                .findById(driverId)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("There is no driver with id {0}", driverId)));
    }

    public AvailableCarsDto getAvailableCars(Driver driver) {
        if(driver.getDriverStatus() != DriverStatus.FREE) {
            throw new IllegalArgumentException("You can not currently perform this operation");
        }

        AvailableCarsDto availableCarsDto = new AvailableCarsDto();
        var userCars = driver.getCars()
                .stream()
                .map(car -> new CarDto(car))
                .collect(Collectors.toList());

        availableCarsDto.setCars(userCars);

        var companiesCars = driver.getDriverCompanyRequests()
                .stream()
                .filter(request -> request.getRequestStatus() == RequestStatus.ACCEPTED)
                .map(request -> request.getCompany())
                .map(company -> new CompanyDto(company, true))
                .collect(Collectors.toList());

        availableCarsDto.setCarsFromCompanies(companiesCars);

        return availableCarsDto;
    }

    public DailyInfo startDriving(Driver driver, DailyInfoRequest dailyInfoRequest) {
        if(driver.getDriverStatus() != DriverStatus.FREE) {
            throw new IllegalArgumentException("You can not perform this operation now");
        }

        Car car = carRepository.findById(dailyInfoRequest.getCarId())
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("Car with id {0} does not exist", dailyInfoRequest.getCarId())));


        if(car.getOwner().getUserType() == "DRIVER") {
            return startDrivingWithoutCompany(driver, car, dailyInfoRequest);
        } else {
            return startDrivingWithCompany(driver, car, dailyInfoRequest);
        }
    }

    private DailyInfo startDrivingWithoutCompany(Driver driver, Car car, DailyInfoRequest dailyInfoRequest) {
        if(car.getOwner().getId() != driver.getId()) {
            throw new IllegalArgumentException(MessageFormat.format("You do not have access to car with id {0}", car.getId()));
        } else if(car.getCarStatus() == CarStatus.BUSY) {
            throw new IllegalArgumentException(MessageFormat.format("Car with id {0} is currently unavailable", car.getId()));
        }

        DailyInfo dailyInfo = new DailyInfo(car, new LatLng(dailyInfoRequest.getLat(), dailyInfoRequest.getLng()), driver);
        driver.setDriverStatus(DriverStatus.WORK);
        driverRepository.save(driver);
        car.setCarStatus(CarStatus.BUSY);
        carRepository.save(car);

        return dailyInfoRepository.save(dailyInfo);
    }

    private DailyInfo startDrivingWithCompany(Driver driver, Car car, DailyInfoRequest dailyInfoRequest) {
        Company company = (Company) car.getOwner();

        if(!driverCompanyRequestRepository.existsByDriverAndAndCompany(driver, company)) {
            throw new IllegalArgumentException(MessageFormat.format("You do not have access to car with id {0}", car.getId()));
        } else if(car.getCarStatus() == CarStatus.BUSY) {
            throw new IllegalArgumentException(MessageFormat.format("Car with id {0} is currently unavailable", car.getId()));
        }

        DailyInfo dailyInfo = new DailyInfo(car, new LatLng(dailyInfoRequest.getLat(), dailyInfoRequest.getLng()), driver, company);
        driver.setDriverStatus(DriverStatus.WORK);
        driverRepository.save(driver);
        car.setCarStatus(CarStatus.BUSY);
        carRepository.save(car);

        return dailyInfoRepository.save(dailyInfo);
    }

    public DailyInfo endDriving(Driver driver) {
        DailyInfo dailyInfo = dailyInfoRepository.findByDriverAndEndTimeIsNull(driver)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("You are not driving currently", driver.getUsername())));

        dailyInfo.setEndTime(new Date());
        dailyInfoRepository.save(dailyInfo);
        Car car = dailyInfo.getCar();
        car.setCarStatus(CarStatus.FREE);
        carRepository.save(car);
        driver.setDriverStatus(DriverStatus.FREE);
        driverRepository.save(driver);

        return dailyInfo;
    }

    public DailyInfo updatePosition(Driver driver, LocationUpdateRequest locationUpdateRequest) {
        DailyInfo dailyInfo = dailyInfoRepository.findByDriverAndEndTimeIsNull(driver)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("You are not driving currently", driver.getUsername())));

        dailyInfo.setPosition(new LatLng(locationUpdateRequest.getLat(), locationUpdateRequest.getLng()));
        dailyInfoRepository.save(dailyInfo);

        return dailyInfo;
    }
}
