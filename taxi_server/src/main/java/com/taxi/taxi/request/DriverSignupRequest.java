package com.taxi.taxi.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class DriverSignupRequest extends SignupRequest {
    @NotBlank
    @Size(min = 2, max = 40)
    private String name;

    @NotBlank
    @Size(min = 4, max = 40)
    private String surname;

    @NotBlank
    @Size(max = 50)
    private String city;

    public DriverSignupRequest(String username, String email, String password, String name, String surname, String city, String phoneNumber) {
        super(username, email, password,phoneNumber);
        this.name = name;
        this.surname = surname;
        this.city = city;
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
}
