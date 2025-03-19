package com.logistic.delivery.adapter.out.persistence.repository;

import com.logistic.delivery.adapter.out.persistence.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryJpaRepository extends JpaRepository<DeliveryEntity, Long> {
}
