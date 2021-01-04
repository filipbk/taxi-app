package com.taxi.taxi.controller;

import com.taxi.taxi.dto.PriceDto;
import com.taxi.taxi.model.Price;
import com.taxi.taxi.model.User;
import com.taxi.taxi.request.ApiResponse;
import com.taxi.taxi.request.PriceRequest;
import com.taxi.taxi.security.CurrentUser;
import com.taxi.taxi.security.UserPrincipal;
import com.taxi.taxi.service.AuthenticationService;
import com.taxi.taxi.service.PriceService;
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
@RequestMapping("/prices")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @Autowired
    private AuthenticationService userService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('DRIVER') or hasRole('COMPANY')")
    public ResponseEntity<?> addPrice(@Valid @RequestBody PriceRequest priceRequest, @CurrentUser UserPrincipal userPrincipal) {
        PriceDto price;

        try {
            User user = userService.getUser(userPrincipal.getId());
            price = priceService.addPrice(priceRequest, user);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/{priceId}")
                .buildAndExpand(price.getId()).toUri();

        return ResponseEntity
                .created(uri)
                .body(new ApiResponse(true, "Price added successfully", price));
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('DRIVER') or hasRole('COMPANY')")
    public List<Price> getUserPrices(@CurrentUser UserPrincipal userPrincipal) {
        User user = userService.getUser(userPrincipal.getId());

        return priceService.getUserPrices(user);
    }
}
