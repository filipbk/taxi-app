package com.taxi.taxi.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CompanySignupRequest extends SignupRequest {
    @NotBlank
    @Size(min = 4, max = 40)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String city;

    public CompanySignupRequest(String username, String email, String password, String name, String city, String phoneNumber) {
        super(username, email, password, phoneNumber);
        this.name = name;
        this.city = city;
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
}
