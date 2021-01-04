package com.taxi.taxi.dto;

import com.taxi.taxi.model.*;

public class StatusDto implements Dto {
    private Long userId;

    private Status status;

    private String type;

    public StatusDto(Driver driver) {
        this.userId = driver.getId();
        this.status = driver.getDriverStatus();
        this.type = driver.getUserType();
    }

    public StatusDto(Customer customer) {
        this.userId = customer.getId();
        this.status = customer.getCustomerStatus();
        this.type = customer.getUserType();
    }

    public StatusDto(Company company) {
        this.userId = company.getId();
        this.type = company.getUserType();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
