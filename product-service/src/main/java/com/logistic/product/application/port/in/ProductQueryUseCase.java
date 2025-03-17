package com.logistic.product.application.port.in;

import com.logistic.product.application.port.in.query.ProductFindQuery;
import com.logistic.product.application.port.in.query.ProductSearchQuery;
import com.logistic.product.domain.Product;
import org.springframework.data.domain.Page;

public interface ProductQueryUseCase {
  Product findProduct(ProductFindQuery query);

  Page<Product> search(ProductSearchQuery query);
}
