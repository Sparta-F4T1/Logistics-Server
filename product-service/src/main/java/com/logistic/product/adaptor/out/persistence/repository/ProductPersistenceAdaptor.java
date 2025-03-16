package com.logistic.product.adaptor.out.persistence.repository;

import com.logistic.common.annotation.PersistenceAdapter;
import com.logistic.product.adaptor.in.web.request.ProductSearchRequest;
import com.logistic.product.adaptor.out.persistence.ProductEntity;
import com.logistic.product.adaptor.out.persistence.mapper.ProductPersistenceMapper;
import com.logistic.product.application.port.out.ProductPersistencePort;
import com.logistic.product.domain.Product;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@PersistenceAdapter
@RequiredArgsConstructor
public class ProductPersistenceAdaptor implements ProductPersistencePort {
  private final ProductJpaRepository jpaRepository;
  private final ProductQueryDslRepository queryDslRepository;
  private final ProductPersistenceMapper productPersistenceMapper;

  @Override
  public Product save(final Product product) {
    final ProductEntity productEntity = jpaRepository.save(productPersistenceMapper.toEntity(product));
    return productPersistenceMapper.toDomain(productEntity);
  }

  @Override
  public Optional<Product> findById(final Long productId) {
    return jpaRepository.findById(productId).map(productPersistenceMapper::toDomain);
  }

  @Override
  public Page<Product> search(final ProductSearchRequest request, final Pageable pageable) {
    return queryDslRepository.search(request, pageable).map(productPersistenceMapper::toDomain);
  }
}
