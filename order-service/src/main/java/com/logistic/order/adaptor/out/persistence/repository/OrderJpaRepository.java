package com.logistic.order.adaptor.out.persistence.repository;

import com.logistic.order.adaptor.out.persistence.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface
OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
}
