package com.taxi.taxi.repository;

import com.taxi.taxi.model.DailyInfo;
import com.taxi.taxi.model.Driver;
import com.taxi.taxi.model.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DailyInfoRepository extends JpaRepository<DailyInfo, Long> {
    Optional<DailyInfo> findByDriverAndEndTimeIsNull(Driver driver);

    @Query("select d from DailyInfo d where d.driver.city = ?1 and d.endTime = null and d.driver.driverStatus = ?2 and d.company = null")
    List<DailyInfo> findFreeDriversByCity(String city, DriverStatus driverStatus);

    @Query("select d from DailyInfo d where d.company.city = ?1 and d.endTime = null and d.driver.driverStatus = ?2")
    List<DailyInfo> findFreeDriversByCompanyCity(String city, DriverStatus driverStatus);

    Optional<DailyInfo> findById(Long id);
}
