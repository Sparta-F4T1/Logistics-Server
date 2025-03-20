package com.logistic.product.adapter.out.persistence.repository;

import com.logistic.product.adapter.out.persistence.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}
