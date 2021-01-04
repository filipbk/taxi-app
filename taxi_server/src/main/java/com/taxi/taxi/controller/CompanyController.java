package com.taxi.taxi.controller;

import com.taxi.taxi.dto.AvailableCarsDto;
import com.taxi.taxi.dto.CompanyDto;
import com.taxi.taxi.dto.DriverCompanyRequestDto;
import com.taxi.taxi.model.Company;
import com.taxi.taxi.model.Driver;
import com.taxi.taxi.model.DriverCompanyRequest;
import com.taxi.taxi.request.ApiResponse;
import com.taxi.taxi.request.CompanyByCityRequest;
import com.taxi.taxi.security.CurrentUser;
import com.taxi.taxi.security.UserPrincipal;
import com.taxi.taxi.service.CompanyService;
import com.taxi.taxi.service.DriverService;
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
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private DriverService driverService;

    @PostMapping("/like")
    @PreAuthorize("hasRole('DRIVER')")
    public List<CompanyDto> getCompaniesThatContains(@Valid @RequestBody CompanyByCityRequest companyByCityRequest) {
        return companyService.getCompaniesThatContain(companyByCityRequest.getCity());
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('DRIVER')")
    public List<CompanyDto> getCompaniesByCity(@Valid @RequestBody CompanyByCityRequest companyByCityRequest) {
        return companyService.getCompaniesByCity(companyByCityRequest.getCity());
    }

    @GetMapping("/request/{companyId}")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<?> addDriverCompanyRequest(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long companyId) {
        DriverCompanyRequest driverCompanyRequest;

        try {
            Driver driver = driverService.getDriverFromDb(userPrincipal.getId());
            driverCompanyRequest = companyService.addDriverCompanyRequest(driver, companyId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }



        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/{driverCompanyRequestId}")
                .buildAndExpand(driverCompanyRequest.getId()).toUri();

        return ResponseEntity
                .created(uri)
                .body(new ApiResponse(true, "Company join request added successfully"));
    }

    @GetMapping("/request/waiting")
    @PreAuthorize("hasRole('COMPANY')")
    public List<DriverCompanyRequestDto> getNewRequestsToAccept(@CurrentUser UserPrincipal userPrincipal) {
        Company company = companyService.getComapnyfromDb(userPrincipal.getId());

        return companyService.getNewRequestsToAccept(company);
    }

    @GetMapping("/request/reject/{requestId}")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<?> rejectRequest(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long requestId) {
        Company company = companyService.getComapnyfromDb(userPrincipal.getId());
        DriverCompanyRequest driverCompanyRequest;

        try {
            driverCompanyRequest = companyService.getDriverCompanyRequest(company, requestId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        companyService.rejectRequest(driverCompanyRequest);

        return new ResponseEntity<>(new ApiResponse(true, "Request rejected successfully"), HttpStatus.OK);
    }

    @GetMapping("/request/accept/{requestId}")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<?> acceptRequest(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long requestId) {
        Company company = companyService.getComapnyfromDb(userPrincipal.getId());
        DriverCompanyRequest driverCompanyRequest;

        try {
            driverCompanyRequest = companyService.getDriverCompanyRequest(company, requestId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        companyService.acceptRequest(driverCompanyRequest, company);

        return new ResponseEntity<>(new ApiResponse(true, "Request accepted successfully"), HttpStatus.OK);
    }

    @GetMapping("/employees")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<?> getEmployees(@CurrentUser UserPrincipal userPrincipal) {
        Company company = companyService.getComapnyfromDb(userPrincipal.getId());
        var employees = companyService.getCompanyEmployees(company);

        return new ResponseEntity<>(new ApiResponse(true, "Employees list", employees), HttpStatus.OK);
    }

    @GetMapping("/cars")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<?> getAvailableCars(@CurrentUser UserPrincipal userPrincipal) {
        try {
            Company company = companyService.getComapnyfromDb(userPrincipal.getId());
            AvailableCarsDto availableCarsDto = companyService.getAvailableCars(company);

            return new ResponseEntity<>(new ApiResponse(true, "Available cars", availableCarsDto), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
