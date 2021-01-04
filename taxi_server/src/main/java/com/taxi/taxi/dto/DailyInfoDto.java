package com.taxi.taxi.dto;

import com.taxi.taxi.model.DailyInfo;
import com.taxi.taxi.model.Driver;
import com.taxi.taxi.util.TimeDistanceInfo;

public class DailyInfoDto implements Dto {
    private Long id;

    private DriverDto driver;

    private CarDto car;

    private CompanyDto company;

    private TimeDistanceInfo timeDistanceInfo;

    public DailyInfoDto(DailyInfo dailyInfo) {
        this.id = dailyInfo.getId();
        this.driver = new DriverDto(dailyInfo.getDriver());
        this.car = new CarDto(dailyInfo.getCar());

        if(dailyInfo.getCompany() != null) {
            this.company = new CompanyDto(dailyInfo.getCompany(), false);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DriverDto getDriver() {
        return driver;
    }

    public void setDriver(DriverDto driver) {
        this.driver = driver;
    }

    public CarDto getCar() {
        return car;
    }

    public void setCar(CarDto car) {
        this.car = car;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    public TimeDistanceInfo getTimeDistanceInfo() {
        return timeDistanceInfo;
    }

    public void setTimeDistanceInfo(TimeDistanceInfo timeDistanceInfo) {
        this.timeDistanceInfo = timeDistanceInfo;
    }
}
