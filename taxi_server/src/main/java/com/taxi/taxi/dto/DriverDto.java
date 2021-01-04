package com.taxi.taxi.dto;

import com.taxi.taxi.model.Driver;

public class DriverDto implements UserDto, Dto {

    private Long id;

    private String username;

    private String email;

    private String name;

    private String surname;

    private String city;

    private Double rate;

    private Long ratesNumber;

    private String phoneNumber;

    public DriverDto() {}

    public DriverDto(Driver driver) {
        this.id = driver.getId();
        this.username = driver.getUsername();
        this.email = driver.getEmail();
        this.name = driver.getName();
        this.surname = driver.getSurname();
        this.city = driver.getCity();
        this.rate = driver.getRate();
        this.ratesNumber = driver.getRatesNumber();
        this.phoneNumber = driver.getPhoneNumber();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
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
}
