package com.logistic.driver.adapter.out.persistence.repository;

import com.logistic.driver.adapter.out.persistence.model.entity.DriverViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverViewJpaRepository extends JpaRepository<DriverViewEntity, String> {
}
