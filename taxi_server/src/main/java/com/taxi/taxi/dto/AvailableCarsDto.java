package com.taxi.taxi.dto;

import java.util.List;

public class AvailableCarsDto implements Dto {
    private List<CarDto> cars;

    private  List<CompanyDto> carsFromCompanies;

    public AvailableCarsDto() {}

    public AvailableCarsDto(List<CarDto> cars) {
        this.cars = cars;
    }

    public AvailableCarsDto(List<CarDto> cars, List<CompanyDto> carsFromCompanies) {
        this.cars = cars;
        this.carsFromCompanies = carsFromCompanies;
    }

    public List<CarDto> getCars() {
        return cars;
    }

    public void setCars(List<CarDto> cars) {
        this.cars = cars;
    }

    public List<CompanyDto> getCarsFromCompanies() {
        return carsFromCompanies;
    }

    public void setCarsFromCompanies(List<CompanyDto> carsFromCompanies) {
        this.carsFromCompanies = carsFromCompanies;
    }
}
