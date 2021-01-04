package com.taxi.taxi.repository;

import com.taxi.taxi.model.Car;
import com.taxi.taxi.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findById(Long id);

    List<Car> findByOwnerId(Long ownerId);
}
