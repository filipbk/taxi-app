package com.taxi.taxi.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("company")
public class Company extends User {
    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String city;

    private Double rate;

    private Long ratesNumber;

    @OneToMany(mappedBy = "owner")
    private Set<Car> cars;

    @OneToMany(mappedBy = "company")
    private Set<DailyInfo> dailyInfos;

    @OneToMany(mappedBy = "company")
    private Set<DriverCompanyRequest> driverCompanyRequests;

    @OneToMany(mappedBy = "user")
    private Set<Price> prices;

    public Company() {}

    public Company(String username, String email, String password, String name, String city, Double rate, Long ratesNumber, String phoneNumber) {
        super(username, email, password, phoneNumber);
        this.name = name;
        this.city = city;
        this.rate = rate;
        this.ratesNumber = ratesNumber;
        this.cars = new HashSet<>();
        this.dailyInfos = new HashSet<>();
        this.driverCompanyRequests = new HashSet<>();
        this.prices = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setRatesNumber(Long rates_number) {
        this.ratesNumber = rates_number;
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

    @Override
    public String getUserType() {
        return "COMPANY";
    }
}
