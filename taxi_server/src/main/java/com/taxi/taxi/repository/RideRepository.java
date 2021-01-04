package com.taxi.taxi.repository;

import com.taxi.taxi.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RideRepository extends JpaRepository<Ride, Long> {
    boolean existsByCustomerAndRideStatusIn(Customer customer, List<RideStatus> rideStatus);

    Optional<Ride> findByCustomerAndRideStatusIn(Customer customer, List<RideStatus> rideStatus);

    Optional<Ride> findByDailyInfoDriverAndRideStatusIn(Driver driver, List<RideStatus> rideStatus);

    Optional<Ride> findByCustomerAndDailyInfoId(Customer customer, Long dailyInfoId);

    Optional<Ride> findByCustomer(Customer customer);

    Optional<Ride> findByCustomerAndIdAndOpinionIsNull(Customer customer, Long id);

    Optional<Ride> findByDailyInfoDriverAndRideStatus(Driver driver, RideStatus rideStatus);

    Optional<Ride> findByDailyInfoDriver(Driver driver);

    Optional<Ride> findByCustomerAndRideStatusIn(Customer customer, RideStatus rideStatus);

    Optional<Ride> findByIdAndOpinionIsNotNull(Long id);

    List<Ride> findAllByCustomer(Customer customer);

    List<Ride> findAllByDailyInfoDriver(Driver driver);

    List<Ride> findAllByDailyInfoCompany(Company company);
}
