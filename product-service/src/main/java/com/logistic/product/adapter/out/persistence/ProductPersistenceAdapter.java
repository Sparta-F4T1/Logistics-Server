package com.logistic.product.adapter.out.persistence;

import com.logistic.common.annotation.Adapter;
import com.logistic.product.adapter.out.persistence.mapper.ProductPersistenceMapper;
import com.logistic.product.adapter.out.persistence.repository.ProductJpaRepository;
import com.logistic.product.adapter.out.persistence.repository.ProductQueryDslRepository;
import com.logistic.product.application.port.in.query.SearchProductQuery;
import com.logistic.product.application.port.out.ProductPersistencePort;
import com.logistic.product.domain.Product;
import com.logistic.product.domain.exception.DomainException.CompanyNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@Adapter
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductPersistencePort {
  private final ProductJpaRepository jpaRepository;
  private final ProductQueryDslRepository queryDslRepository;
  private final ProductPersistenceMapper mapper;

  @Override
  public Product save(final Product product) {
    return mapper.toDomain(jpaRepository.save(mapper.toEntity(product)));
  }

  @Override
  public Product findById(final Long productId) {
    return mapper.toDomain(jpaRepository.findById(productId)
        .orElseThrow(CompanyNotFoundException::new));
  }

  @Override
  public List<Product> findAll(final List<Long> productIds) {
    return jpaRepository.findAllById(productIds).stream().map(mapper::toDomain).toList();
  }

  @Override
  public Page<Product> search(final SearchProductQuery query) {
    return queryDslRepository.search(query).map(mapper::toDomain);
  }
}
