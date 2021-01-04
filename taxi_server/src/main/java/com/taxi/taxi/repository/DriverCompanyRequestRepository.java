package com.taxi.taxi.repository;

import com.taxi.taxi.model.Company;
import com.taxi.taxi.model.Driver;
import com.taxi.taxi.model.DriverCompanyRequest;
import com.taxi.taxi.model.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverCompanyRequestRepository extends JpaRepository<DriverCompanyRequest, Long> {
    public Boolean existsByDriverAndAndCompanyAndRequestStatus(Driver driver, Company company, RequestStatus requestStatus);

    public Boolean existsByDriverAndAndCompany(Driver driver, Company company);

    public List<DriverCompanyRequest> findByCompanyAndRequestStatus(Company company, RequestStatus requestStatus);

    public Optional<DriverCompanyRequest> findByIdAndCompany(Long id, Company company);
}
