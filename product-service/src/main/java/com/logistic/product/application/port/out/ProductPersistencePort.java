package com.logistic.product.application.port.out;

import com.logistic.product.adaptor.in.web.request.ProductSearchRequest;
import com.logistic.product.domain.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductPersistencePort {
  Product save(Product product);

  Optional<Product> findById(Long productId);

  Page<Product> search(ProductSearchRequest request, Pageable pageable);
}
