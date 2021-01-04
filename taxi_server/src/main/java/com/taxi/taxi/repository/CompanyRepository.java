package com.taxi.taxi.repository;

import com.taxi.taxi.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository  extends JpaRepository<Company, Long> {
    List<Company> findByCityContaining(String city);

    List<Company> findByCity(String city);

    public Optional<Company> findById(Long id);
}
