package com.taxi.taxi.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CarModelName extends DBTimestamps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "car_producer_id")
    private CarProducer carProducer;

    @OneToMany(mappedBy = "carModelName")
    private Set<Car> cars;

    public CarModelName() {}

    public CarModelName(@NotNull String name, @NotNull CarProducer carProducer) {
        this.name = name;
        this.carProducer = carProducer;
        this.cars = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CarProducer getCarProducer() {
        return carProducer;
    }

    public void setCarProducer(CarProducer carProducer) {
        this.carProducer = carProducer;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }
}
