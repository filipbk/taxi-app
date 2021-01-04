package com.taxi.taxi.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CompanyByCityRequest {

    @NotBlank
    @Size(max = 50)
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
