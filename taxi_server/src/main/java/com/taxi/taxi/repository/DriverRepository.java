package com.taxi.taxi.repository;

import com.taxi.taxi.model.Company;
import com.taxi.taxi.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Max;
import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    public Optional<Driver> findById(Long id);
}
