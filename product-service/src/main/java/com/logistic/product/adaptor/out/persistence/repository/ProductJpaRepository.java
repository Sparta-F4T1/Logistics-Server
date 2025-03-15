package com.logistic.product.adaptor.out.persistence.repository;

import com.logistic.product.adaptor.out.persistence.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}
