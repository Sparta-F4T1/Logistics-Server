package com.logistic.driver.adapter.out.persistence.repository;

import com.logistic.driver.adapter.out.persistence.model.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverJpaRepository extends JpaRepository<DriverEntity, String> {
}
