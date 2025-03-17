package com.logistic.product.application.port.out;

import com.logistic.product.application.port.in.query.ProductSearchQuery;
import com.logistic.product.domain.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ProductPersistencePort {
  Product save(Product product);

  Optional<Product> findById(Long productId);

  Page<Product> search(ProductSearchQuery productSearchQuery);
}
