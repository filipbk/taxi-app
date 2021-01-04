package com.taxi.taxi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "car")
public class Car extends DBTimestamps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String producer;

    @NotBlank
    @Size(max = 50)
    private String modelName;

    @NotNull
    private Long productionYear;

    @NotBlank
    @Size(max = 50)
    private String registrationNumber;

    @NotBlank
    @Size(max = 50)
    private String color;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User owner;

    @NotNull
    @JsonIgnore
    private CarStatus carStatus;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "price_id")
    @JsonIgnore
    private Price price;

    @ManyToOne
    @JoinColumn(name = "car_producer_id")
    @JsonIgnore
    private CarProducer carProducer;

    @ManyToOne
    @JoinColumn(name = "car_model_name_id")
    @JsonIgnore
    private CarModelName carModelName;

    public Car() {}

    public Car(String producer, String modelName, Long productionYear, String registrationNumber, String color, User owner, Price price) {
        this.producer = producer;
        this.modelName = modelName;
        this.productionYear = productionYear;
        this.registrationNumber = registrationNumber;
        this.color = color;
        this.owner = owner;
        this.carStatus = CarStatus.FREE;
        this.price = price;
    }

    public Car(String producer, String modelName, Long productionYear, String registrationNumber, String color, User owner, Price price, CarProducer carProducer, CarModelName carModelName) {
        this.producer = producer;
        this.modelName = modelName;
        this.productionYear = productionYear;
        this.registrationNumber = registrationNumber;
        this.color = color;
        this.owner = owner;
        this.carStatus = CarStatus.FREE;
        this.price = price;
        this.carProducer = carProducer;
        this.carModelName = carModelName;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public CarStatus getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(CarStatus carStatus) {
        this.carStatus = carStatus;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public CarProducer getCarProducer() {
        return carProducer;
    }

    public void setCarProducer(CarProducer carProducer) {
        this.carProducer = carProducer;
    }

    public CarModelName getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(CarModelName carModelName) {
        this.carModelName = carModelName;
    }
}
