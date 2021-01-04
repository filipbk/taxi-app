package com.taxi.taxi.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CustomerSignupRequest extends SignupRequest {
    @NotBlank
    @Size(min = 2, max = 40)
    private String name;

    @NotBlank
    @Size(min = 4, max = 40)
    private String surname;

    public CustomerSignupRequest(String username, String email, String password, String name, String surname, String phoneNumber) {
        super(username, email, password, phoneNumber);
        this.name = name;
        this.surname = surname;
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
}
