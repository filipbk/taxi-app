package com.taxi.taxi.controller;

import com.taxi.taxi.dto.AvailableCarsDto;
import com.taxi.taxi.model.DailyInfo;
import com.taxi.taxi.model.Driver;
import com.taxi.taxi.request.ApiResponse;
import com.taxi.taxi.request.DailyInfoRequest;
import com.taxi.taxi.request.LocationUpdateRequest;
import com.taxi.taxi.security.CurrentUser;
import com.taxi.taxi.security.UserPrincipal;
import com.taxi.taxi.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/driver")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @GetMapping("/cars")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<?> getAvailableCars(@CurrentUser UserPrincipal userPrincipal) {
        AvailableCarsDto availableCarsDto;

        try {
            Driver driver = driverService.getDriverFromDb(userPrincipal.getId());
            availableCarsDto = driverService.getAvailableCars(driver);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ApiResponse(true, "Available cars", availableCarsDto), HttpStatus.OK);
    }

    @PostMapping("/start_driving")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<?> startDriving(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody DailyInfoRequest dailyInfoRequest) {
        DailyInfo dailyInfo;

        try {
            Driver driver = driverService.getDriverFromDb(userPrincipal.getId());
            dailyInfo = driverService.startDriving(driver, dailyInfoRequest);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }



        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/{dailyInfoId}")
                .buildAndExpand(dailyInfo.getId()).toUri();

        return ResponseEntity
                .created(uri)
                .body(new ApiResponse(true, "Driving registered"));
    }

    @GetMapping("/end_driving")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<?> endDriving(@CurrentUser UserPrincipal userPrincipal) {
        try {
            Driver driver = driverService.getDriverFromDb(userPrincipal.getId());
            driverService.endDriving(driver);

            return new ResponseEntity<>(new ApiResponse(true, "You stopped driving, you have FREE status"), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update_position")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<?> updatePosition(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody LocationUpdateRequest locationUpdateRequest) {
        try {
            Driver driver = driverService.getDriverFromDb((userPrincipal.getId()));
            driverService.updatePosition(driver, locationUpdateRequest);

            return new ResponseEntity<>(new ApiResponse(true, "Position updated successfully"), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
