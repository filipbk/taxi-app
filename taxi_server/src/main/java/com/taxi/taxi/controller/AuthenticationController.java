package com.taxi.taxi.controller;

import com.taxi.taxi.model.*;
import com.taxi.taxi.request.*;
import com.taxi.taxi.security.CurrentUser;
import com.taxi.taxi.security.JwtTokenProvider;
import com.taxi.taxi.security.UserPrincipal;
import com.taxi.taxi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.text.MessageFormat;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsernameOrEmail(),
                                loginRequest.getPassword()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication((authentication));
        String jwt = tokenProvider.generateToken(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long id = userPrincipal.getId();
        User user = authenticationService.getUser(id);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, user));
    }

    @PostMapping("/customer_signup")
    public ResponseEntity<?> register(@Valid @RequestBody CustomerSignupRequest signupRequest) {
        User newUser;

        try {
            Customer customer = authenticationService.mapToCustomer(signupRequest);
            newUser = authenticationService.register(customer, RoleName.ROLE_CUSTOMER);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{username}")
                .buildAndExpand(newUser.getUsername())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(new ApiResponse(true, MessageFormat.format("User {0} registered successfully", newUser.getUsername())));
    }

    @PostMapping("/driver_signup")
    public ResponseEntity<?> register(@Valid @RequestBody DriverSignupRequest signupRequest) {
        User newUser;

        try {
            Driver driver = authenticationService.mapToDriver(signupRequest);
            newUser = authenticationService.register(driver, RoleName.ROLE_DRIVER);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{username}")
                .buildAndExpand(newUser.getUsername())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(new ApiResponse(true, MessageFormat.format("User {0} registered successfully", newUser.getUsername())));
    }

    @PostMapping("/company_signup")
    public ResponseEntity<?> register(@Valid @RequestBody CompanySignupRequest signupRequest) {
        User newUser;

        try {
            Company company = authenticationService.mapToCompany(signupRequest);
            newUser = authenticationService.register(company, RoleName.ROLE_COMPANY);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{username}")
                .buildAndExpand(newUser.getUsername())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(new ApiResponse(true, MessageFormat.format("User {0} registered successfully", newUser.getUsername())));
    }

    @GetMapping("/status")
    @PreAuthorize("hasRole('DRIVER') or hasRole('COMPANY') or hasRole('CUSTOMER')")
    public ResponseEntity<?> getUserStatus(@CurrentUser UserPrincipal userPrincipal) {
        User user;

        try {
            user = authenticationService.getUser(userPrincipal.getId());
            return new ResponseEntity<>(new ApiResponse(true, "User status", authenticationService.getUserStatus(user)), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
