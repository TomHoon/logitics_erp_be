package com.logitics.erp.driver.repository;

import com.logitics.erp.driver.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}
