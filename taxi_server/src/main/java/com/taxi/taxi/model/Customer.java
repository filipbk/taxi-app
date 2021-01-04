package com.taxi.taxi.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("customer")
public class Customer extends User {

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String surname;

    @OneToMany(mappedBy = "customer")
    private Set<Ride> rides;

    private CustomerStatus customerStatus;

    public Customer() {}

    public Customer(String username, String email, String password, String name, String surname, String phoneNumber) {
        super(username, email, password, phoneNumber);
        this.name = name;
        this.surname = surname;
        this.rides = new HashSet<>();
        this.customerStatus = CustomerStatus.FREE;
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

    public Set<Ride> getRides() {
        return rides;
    }

    public void setRides(Set<Ride> rides) {
        this.rides = rides;
    }

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(CustomerStatus customerStatus) {
        this.customerStatus = customerStatus;
    }

    @Override
    public String getUserType() {
        return "CUSTOMER";
    }
}
