package com.taxi.taxi.repository;

import com.taxi.taxi.model.Price;
import com.taxi.taxi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {
    Optional<Price> findById(Long id);

    List<Price> findByUserId(Long userId);

    Optional<Price> findByIdAndUser(Long id, User user);
}
