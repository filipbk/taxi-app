package com.taxi.taxi.model;

import com.taxi.taxi.request.AvailableDriversRequest;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("driver")
public class Driver extends User {
    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String surname;

    @NotBlank
    @Size(max = 50)
    private String city;

    private Double rate;

    private Long ratesNumber;

    @OneToMany(mappedBy = "owner")
    private Set<Car> cars;

    @OneToMany(mappedBy = "driver")
    private Set<DailyInfo> dailyInfos;

    @OneToMany(mappedBy = "driver")
    private Set<DriverCompanyRequest> driverCompanyRequests;

    @OneToMany(mappedBy = "user")
    private Set<Price> prices;

    private DriverStatus driverStatus;

    public Driver() {}

    public Driver(String username, String email, String password, String name, String surname, String city, Double rate, Long ratesNumber, String phoneNumber) {
        super(username, email, password, phoneNumber);
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.rate = rate;
        this.ratesNumber = ratesNumber;
        this.cars = new HashSet<>();
        this.dailyInfos = new HashSet<>();
        this.driverCompanyRequests = new HashSet<>();
        this.prices = new HashSet<>();
        this.driverStatus = DriverStatus.FREE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Long getRatesNumber() {
        return ratesNumber;
    }

    public void setRatesNumber(Long ratesNumber) {
        this.ratesNumber = ratesNumber;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    public Set<DailyInfo> getDailyInfos() {
        return dailyInfos;
    }

    public void setDailyInfos(Set<DailyInfo> dailyInfos) {
        this.dailyInfos = dailyInfos;
    }

    public Set<DriverCompanyRequest> getDriverCompanyRequests() {
        return driverCompanyRequests;
    }

    public void setDriverCompanyRequests(Set<DriverCompanyRequest> driverCompanyRequests) {
        this.driverCompanyRequests = driverCompanyRequests;
    }

    public Set<Price> getPrices() {
        return prices;
    }

    public void setPrices(Set<Price> prices) {
        this.prices = prices;
    }

    public void addPrice(Price price) {
        this.prices.add(price);
    }

    public DriverStatus getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(DriverStatus driverStatus) {
        this.driverStatus = driverStatus;
    }

    @Override
    public String getUserType() {
        return "DRIVER";
    }
}
