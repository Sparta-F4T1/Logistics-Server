package com.logistic.product.application.port.out;

import com.logistic.product.domain.Product;

public interface ProductPersistencePort {
  Product save(Product product);
}
