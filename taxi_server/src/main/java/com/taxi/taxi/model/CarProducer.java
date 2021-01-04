package com.taxi.taxi.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CarProducer extends DBTimestamps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "carProducer")
    private Set<CarModelName> modelNames;

    @OneToMany(mappedBy = "carProducer")
    private Set<Car> cars;

    public CarProducer() {}

    public CarProducer(@NotNull String name, Set<CarModelName> modelNames) {
        this.name = name;
        this.modelNames = new HashSet<>();
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

    public Set<CarModelName> getModelNames() {
        return modelNames;
    }

    public void setModelNames(Set<CarModelName> modelNames) {
        this.modelNames = modelNames;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }
}
