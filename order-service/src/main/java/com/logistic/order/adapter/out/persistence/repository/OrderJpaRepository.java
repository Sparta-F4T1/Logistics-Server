package com.logistic.order.adapter.out.persistence.repository;

import com.logistic.order.adapter.out.persistence.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface
OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
}
