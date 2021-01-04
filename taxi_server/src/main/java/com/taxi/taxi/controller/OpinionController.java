package com.taxi.taxi.controller;

import com.taxi.taxi.dto.OpinionDto;
import com.taxi.taxi.model.Customer;
import com.taxi.taxi.request.ApiResponse;
import com.taxi.taxi.request.OpinionRequest;
import com.taxi.taxi.security.CurrentUser;
import com.taxi.taxi.security.UserPrincipal;
import com.taxi.taxi.service.CustomerService;
import com.taxi.taxi.service.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/opinion")
public class OpinionController {
    @Autowired
    private OpinionService opinionService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> addOpinion(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody OpinionRequest opinionRequest) {
        try {
            Customer customer = customerService.getCustomerFromDb(userPrincipal.getId());
            OpinionDto opinion = opinionService.addOpinion(customer, opinionRequest);

            return new ResponseEntity<>(new ApiResponse(true, "Opinia została dodana pomyślnie", opinion), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('COMPANY') or hasRole('DRIVER')")
    public ResponseEntity<?> getOpinionsForUser( @PathVariable Long userId) {
        try {
            List<OpinionDto> opinions = opinionService.getOpinionsForUser(userId);

            return new ResponseEntity<>(new ApiResponse(true, "Lista opinii", opinions), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ride/{rideId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('COMPANY') or hasRole('DRIVER')")
    public ResponseEntity<?> getOpinionsForRide( @PathVariable Long rideId) {
        try {
            OpinionDto opinion = opinionService.getOpinionsForRide(rideId);

            return new ResponseEntity<>(new ApiResponse(true, "Opinia dla przejazdu", opinion), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
