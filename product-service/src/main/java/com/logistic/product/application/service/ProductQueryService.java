package com.logistic.product.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.product.application.port.in.ProductQueryUseCase;
import com.logistic.product.application.port.in.query.FindProductQuery;
import com.logistic.product.application.port.in.query.ListProductQuery;
import com.logistic.product.application.port.in.query.SearchProductQuery;
import com.logistic.product.application.port.out.ProductQueryPersistencePort;
import com.logistic.product.domain.Product;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService implements ProductQueryUseCase {
  private final ProductQueryPersistencePort persistencePort;

  @Override
  public Product findProduct(final FindProductQuery query) {
    return persistencePort.findById(query.productId());
  }

  @Override
  public List<Product> findProductList(final ListProductQuery query) {
    return persistencePort.findAll(query.productIds());
  }

  @Override
  public Page<Product> search(final SearchProductQuery query) {
    return persistencePort.search(query);
  }
}
