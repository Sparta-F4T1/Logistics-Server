package com.logistic.product.application.port.in;

import com.logistic.product.adaptor.in.web.request.ProductSearchRequest;
import com.logistic.product.application.port.in.query.ProductFindQuery;
import com.logistic.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductQueryUseCase {
  Product findProduct(ProductFindQuery findQuery);

  Page<Product> search(ProductSearchRequest request, Pageable pageable);
}
