package com.logistic.product.adaptor.out.persistence.repository;

import com.logistic.common.annotation.PersistenceAdapter;
import com.logistic.product.adaptor.out.persistence.ProductEntity;
import com.logistic.product.adaptor.out.persistence.mapper.ProductPersistenceMapper;
import com.logistic.product.application.port.out.ProductPersistencePort;
import com.logistic.product.domain.Product;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ProductPersistenceAdaptor implements ProductPersistencePort {
  private final ProductJpaRepository jpaRepository;
  private final ProductPersistenceMapper productPersistenceMapper;

  @Override
  public Product save(final Product product) {
    final ProductEntity saved = jpaRepository.save(productPersistenceMapper.toEntity(product));
    return productPersistenceMapper.toDomain(saved);
  }
}
