package com.taxi.taxi.dto;

import com.taxi.taxi.model.Car;

public class CarDto implements Dto {
    private Long id;

    private String producer;

    private String modelName;

    private Long productionYear;

    private String registrationNumber;

    private String color;

    public CarDto(Car car) {
        this.id = car.getId();
        this.producer = car.getProducer();
        this.modelName = car.getModelName();
        this.productionYear = car.getProductionYear();
        this.registrationNumber = car.getRegistrationNumber();
        this.color = car.getColor();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Long getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Long productionYear) {
        this.productionYear = productionYear;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
