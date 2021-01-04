package com.taxi.taxi.service;

import com.taxi.taxi.dto.*;
import com.taxi.taxi.model.Company;
import com.taxi.taxi.model.Driver;
import com.taxi.taxi.model.DriverCompanyRequest;
import com.taxi.taxi.model.RequestStatus;
import com.taxi.taxi.repository.CompanyRepository;
import com.taxi.taxi.repository.DriverCompanyRequestRepository;
import com.taxi.taxi.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DriverCompanyRequestRepository driverCompanyRequestRepository;

    @Autowired
    private DriverRepository driverRepository;

    public List<CompanyDto> getCompaniesThatContain(String city) {
        return companyRepository.findByCityContaining(city)
                .stream()
                .map(this::mapCompanyToDto)
                .collect(Collectors.toList());
    }

    public List<CompanyDto> getCompaniesByCity(String city) {
        return companyRepository.findByCity(city)
                .stream()
                .map(this::mapCompanyToDto)
                .collect(Collectors.toList());
    }

    public Company getComapnyfromDb(Long companyId) {
        return companyRepository
                .findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("There is no company with id {0}", companyId)));
    }

    public DriverCompanyRequest addDriverCompanyRequest(Driver driver, Long companyId) {
        Company company = companyRepository
                .findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("There is no company with id {0}", companyId)));

        if(driverCompanyRequestRepository.existsByDriverAndAndCompanyAndRequestStatus(driver, company, RequestStatus.NEW)) {
            throw new IllegalArgumentException(MessageFormat.format("You have already got waiting request to company {0}", company.getName()));
        }

        if(driverCompanyRequestRepository.existsByDriverAndAndCompanyAndRequestStatus(driver, company, RequestStatus.ACCEPTED)) {
            throw new IllegalArgumentException(MessageFormat.format("You already work for company {0}", company.getName()));
        }

        DriverCompanyRequest driverCompanyRequest = new DriverCompanyRequest(driver, company);

        return driverCompanyRequestRepository.save(driverCompanyRequest);
    }

    public List<DriverCompanyRequestDto> getNewRequestsToAccept(Company company) {
        return driverCompanyRequestRepository
                .findByCompanyAndRequestStatus(company, RequestStatus.NEW)
                .stream()
                .map(this::mapDriverCompanyRequestToDto)
                .collect(Collectors.toList());
    }

    private CompanyDto mapCompanyToDto(Company company) {
        return new CompanyDto(company, false);
    }

    private DriverCompanyRequestDto mapDriverCompanyRequestToDto(DriverCompanyRequest driverCompanyRequest) {
        return new DriverCompanyRequestDto(driverCompanyRequest);
    }

    public DriverCompanyRequest getDriverCompanyRequest(Company company, Long requestId) {
        return driverCompanyRequestRepository
                .findByIdAndCompany(requestId, company)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("You don't have request with id {0}", requestId)));
    }

    public DriverCompanyRequest rejectRequest(DriverCompanyRequest driverCompanyRequest) {
        driverCompanyRequest.setRequestStatus(RequestStatus.REJECTED);

        return driverCompanyRequestRepository.save(driverCompanyRequest);
    }

    public DriverCompanyRequest acceptRequest(DriverCompanyRequest driverCompanyRequest, Company company) {
        driverCompanyRequest.setRequestStatus(RequestStatus.ACCEPTED);

        return driverCompanyRequestRepository.save(driverCompanyRequest);
    }

    public List<DriverDto> getCompanyEmployees(Company company) {
        return driverCompanyRequestRepository
                .findByCompanyAndRequestStatus(company, RequestStatus.ACCEPTED)
                .stream()
                .map(DriverCompanyRequest::getDriver)
                .map(this::mapDriverToDto)
                .collect(Collectors.toList());
    }

    public DriverDto mapDriverToDto(Driver driver) {
        return new DriverDto(driver);
    }


    public AvailableCarsDto getAvailableCars(Company company) {
        var userCars = company.getCars()
                .stream()
                .map(car -> new CarDto(car))
                .collect(Collectors.toList());

        AvailableCarsDto availableCarsDto = new AvailableCarsDto(userCars);

        return availableCarsDto;
    }
}
