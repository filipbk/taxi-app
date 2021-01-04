package com.taxi.taxi.controller;

import com.taxi.taxi.dto.CarDto;
import com.taxi.taxi.model.User;
import com.taxi.taxi.request.ApiResponse;
import com.taxi.taxi.request.CarRequest;
import com.taxi.taxi.security.CurrentUser;
import com.taxi.taxi.security.UserPrincipal;
import com.taxi.taxi.service.AuthenticationService;
import com.taxi.taxi.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private AuthenticationService userService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('DRIVER') or hasRole('COMPANY')")
    public ResponseEntity<?> createCar(@Valid @RequestBody CarRequest carRequest, @CurrentUser UserPrincipal userPrincipal) {
        CarDto car;

        try {
            User user = userService.getUser(userPrincipal.getId());
            car = carService.createCar(carRequest, user);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/{carId}")
                .buildAndExpand(car.getId()).toUri();

        return ResponseEntity
                .created(uri)
                .body(new ApiResponse(true, "Dodawanie samochodu zakończone powodzeniem", car));
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('DRIVER') or hasRole('COMPANY')")
    public ResponseEntity<?> getUserCars(@CurrentUser UserPrincipal userPrincipal) {
        try {
            User user = userService.getUser(userPrincipal.getId());
            List<CarDto> cars = carService.getUserCars(user);

            return new ResponseEntity<>(new ApiResponse(true, "Przejazd zakończony", cars), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
