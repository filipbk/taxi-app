package com.taxi.taxi.service;

import com.taxi.taxi.dto.StatusDto;
import com.taxi.taxi.exception.AppException;
import com.taxi.taxi.model.*;
import com.taxi.taxi.request.CompanySignupRequest;
import com.taxi.taxi.request.CustomerSignupRequest;
import com.taxi.taxi.request.DriverSignupRequest;
import com.taxi.taxi.repository.RoleRepository;
import com.taxi.taxi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collections;

@Service
public class AuthenticationService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User register(User user, RoleName roleName) {
        if(userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException(MessageFormat.format("User {0} already exists", user.getUsername()));
        }

        if(userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException(MessageFormat.format("User with email {0} already exists", user.getEmail()));
        }

        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new AppException("User role not found"));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(role));

        return userRepository.save(user);
    }

    public Driver mapToDriver(DriverSignupRequest signupRequest) {
        return new Driver(signupRequest.getUsername(), signupRequest.getEmail(), signupRequest.getPassword(), signupRequest.getName(), signupRequest.getSurname(), signupRequest.getCity(), null, new Long(0), signupRequest.getPhoneNumber());
    }


    public Customer mapToCustomer(CustomerSignupRequest signupRequest) {
        return new Customer(signupRequest.getUsername(), signupRequest.getEmail(), signupRequest.getPassword(), signupRequest.getName(), signupRequest.getSurname(), signupRequest.getPhoneNumber());
    }


    public Company mapToCompany(CompanySignupRequest signupRequest) {
        return new Company(signupRequest.getUsername(), signupRequest.getEmail(), signupRequest.getPassword(), signupRequest.getName(), signupRequest.getCity(), null, new Long(0), signupRequest.getPhoneNumber());
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("There is no user with id {0}", userId)));
    }

    public StatusDto getUserStatus(User user) {
        if(user.getUserType() == "DRIVER") {
            return new StatusDto((Driver) user);
        } else if(user.getUserType() == "CUSTOMER") {
            return new StatusDto((Customer) user);
        } else {
            return new StatusDto((Company) user);
        }
    }
}
