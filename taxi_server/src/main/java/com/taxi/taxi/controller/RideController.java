package com.taxi.taxi.controller;

import com.taxi.taxi.dto.RideDto;
import com.taxi.taxi.model.Company;
import com.taxi.taxi.model.Customer;
import com.taxi.taxi.model.Driver;
import com.taxi.taxi.model.User;
import com.taxi.taxi.request.ApiResponse;
import com.taxi.taxi.security.CurrentUser;
import com.taxi.taxi.security.UserPrincipal;
import com.taxi.taxi.service.AuthenticationService;
import com.taxi.taxi.service.CustomerService;
import com.taxi.taxi.service.DriverService;
import com.taxi.taxi.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ride")
public class RideController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private RideService rideService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/order/{workingDriverId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> orderATaxi(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long workingDriverId) {
        try {
            Customer customer = customerService.getCustomerFromDb(userPrincipal.getId());
            RideDto ride = rideService.orderATaxi(customer, workingDriverId);

            return new ResponseEntity<>(new ApiResponse(true, "Zamówienie zostało złożone", ride), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/waiting_order")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<?> getWaitingOrder(@CurrentUser UserPrincipal userPrincipal) {
        try {
            Driver driver = driverService.getDriverFromDb(userPrincipal.getId());
            RideDto ride = rideService.getWaitingOrders(driver);

            return new ResponseEntity<>(new ApiResponse(true, "Oczekujące zamówienie", ride), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/current_order")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<?> getCurrentOrder(@CurrentUser UserPrincipal userPrincipal) {
        try {
            Driver driver = driverService.getDriverFromDb(userPrincipal.getId());
            RideDto ride = rideService.getCurrentOrder(driver);

            return new ResponseEntity<>(new ApiResponse(true, "Szczegóły przejazdu", ride), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/current_request")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getCurrentRequest(@CurrentUser UserPrincipal userPrincipal) {
        try {
            Customer customer = customerService.getCustomerFromDb(userPrincipal.getId());
            RideDto ride = rideService.getCurrentOrder(customer);

            return new ResponseEntity<>(new ApiResponse(true, "Szczegóły przejazdu", ride), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/reject_order")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<?> rejectOrder(@CurrentUser UserPrincipal userPrincipal) {
        try {
            Driver driver = driverService.getDriverFromDb(userPrincipal.getId());
            RideDto ride = rideService.rejectOrder(driver);

            return new ResponseEntity<>(new ApiResponse(true, "Zgłoszenie odrzucone", ride), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accept_order")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<?> acceptOrder(@CurrentUser UserPrincipal userPrincipal) {
        try {
            Driver driver = driverService.getDriverFromDb(userPrincipal.getId());
            RideDto ride = rideService.acceptOrder(driver);

            return new ResponseEntity<>(new ApiResponse(true, "Zgłoszenie zaakceptowane", ride), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/cancel_order")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> cancelOrder(@CurrentUser UserPrincipal userPrincipal) {
        try {
            Customer customer = customerService.getCustomerFromDb(userPrincipal.getId());
            RideDto ride = rideService.cancelOrder(customer);

            return new ResponseEntity<>(new ApiResponse(true, "Zamówienie anulowane", ride), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/driver_arrived")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<?> driverArrived(@CurrentUser UserPrincipal userPrincipal) {
        try {
            Driver driver = driverService.getDriverFromDb(userPrincipal.getId());
            RideDto ride = rideService.driverArrived(driver);

            return new ResponseEntity<>(new ApiResponse(true, "Klient został powiadomiony", ride), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/start_ride")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<?> startRide(@CurrentUser UserPrincipal userPrincipal) {
        try {
            Driver driver = driverService.getDriverFromDb(userPrincipal.getId());
            RideDto ride = rideService.startRide(driver);

            return new ResponseEntity<>(new ApiResponse(true, "Przejazd rozpoczęty", ride), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/end_ride")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<?> endRide(@CurrentUser UserPrincipal userPrincipal) {
        try {
            Driver driver = driverService.getDriverFromDb(userPrincipal.getId());
            RideDto ride = rideService.endRide(driver);

            return new ResponseEntity<>(new ApiResponse(true, "Przejazd zakończony", ride), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/rides_history")
    @PreAuthorize("hasRole('DRIVER') or hasRole('CUSTOMER') or hasRole('COMPANY')")
    public ResponseEntity<?> getRidesHistory(@CurrentUser UserPrincipal userPrincipal) {
        try {
            User user = authenticationService.getUser(userPrincipal.getId());
            List<RideDto> result = null;

            if(user.getUserType() == "CUSTOMER") {
                result = rideService.getRidesHistory((Customer) user);
            } else if (user.getUserType() == "DRIVER") {
                result = rideService.getRidesHistory((Driver) user);
            } else if (user.getUserType() == "COMPANY") {
                result = rideService.getRidesHistory((Company) user);
            }

            return new ResponseEntity<>(new ApiResponse(true, "Historia przejazdów", result), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
