package com.logistic.delivery.adaptor.out.persistence.repository;

import com.logistic.delivery.adaptor.out.persistence.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryJpaRepository extends JpaRepository<DeliveryEntity, Long> {
}
