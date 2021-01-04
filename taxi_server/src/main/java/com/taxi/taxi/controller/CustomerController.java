package com.taxi.taxi.controller;

import com.taxi.taxi.dto.AvailableDriversDto;
import com.taxi.taxi.model.Customer;
import com.taxi.taxi.request.ApiResponse;
import com.taxi.taxi.request.AvailableDriversRequest;
import com.taxi.taxi.security.CurrentUser;
import com.taxi.taxi.security.UserPrincipal;
import com.taxi.taxi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/available_drivers")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getAvailableDrivers(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody AvailableDriversRequest availableDriversRequest) {
        try {
            Customer customer = customerService.getCustomerFromDb(userPrincipal.getId());
            AvailableDriversDto availableDriversDto = customerService.getFreeDrivers(availableDriversRequest, customer);

            return new ResponseEntity(new ApiResponse(true, "Dostępni taksówkarze", availableDriversDto), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/cities")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getCities(@CurrentUser UserPrincipal userPrincipal) {
        return new ResponseEntity<>(customerService.getCities(), HttpStatus.OK);
    }
}
