package com.logistic.product.application.port.out;

import com.logistic.product.application.port.in.query.SearchProductQuery;
import com.logistic.product.domain.Product;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ProductCommandPersistencePort {
  Product save(Product product);

  Product findById(Long productId);

  List<Product> findAllByCompanyId(Long companyId);

  Page<Product> search(SearchProductQuery searchProductQuery);
}
