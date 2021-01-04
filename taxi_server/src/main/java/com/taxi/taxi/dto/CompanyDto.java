package com.taxi.taxi.dto;

import com.taxi.taxi.model.CarStatus;
import com.taxi.taxi.model.Company;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyDto implements UserDto {
    private Long id;

    private String username;

    private String email;

    private String name;

    private String city;

    private Double rate;

    private Long ratesNumber;

    private List<CarDto> cars;

    private String phoneNumber;

    public CompanyDto(Company company, boolean carsPresent) {
        this.id = company.getId();
        this.username = company.getUsername();
        this.email = company.getEmail();
        this.name = company.getName();
        this.city = company.getCity();
        this.rate = company.getRate();
        this.ratesNumber = company.getRatesNumber();
        this.phoneNumber = company.getPhoneNumber();

        if(carsPresent) {
            this.cars = company
                    .getCars()
                    .stream()
                    .filter(car -> car.getCarStatus() == CarStatus.FREE)
                    .map(car -> new CarDto(car))
                    .collect(Collectors.toList());
        }
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

    public void setRatesNumber(Long ratesNumber) {
        this.ratesNumber = ratesNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<CarDto> getCars() {
        return cars;
    }

    public void setCars(List<CarDto> cars) {
        this.cars = cars;
    }
}
