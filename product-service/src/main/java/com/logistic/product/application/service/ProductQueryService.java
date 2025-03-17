package com.logistic.product.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.product.application.port.in.ProductQueryUseCase;
import com.logistic.product.application.port.in.query.ProductFindQuery;
import com.logistic.product.application.port.in.query.ProductSearchQuery;
import com.logistic.product.application.port.out.ProductPersistencePort;
import com.logistic.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductQueryService implements ProductQueryUseCase {
  private final ProductPersistencePort productPersistencePort;

  @Override
  public Product findProduct(final ProductFindQuery query) {
    return productPersistencePort.findById(query.productId()).orElseThrow(
        // todo 예외 구현
    );
  }

  @Override
  public Page<Product> search(final ProductSearchQuery query) {
    return productPersistencePort.search(query);
  }
}
