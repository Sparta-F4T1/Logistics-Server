package com.logistic.product.application.port.in;

import com.logistic.product.application.port.in.command.ProductCreateCommand;
import com.logistic.product.domain.Product;

public interface ProductUseCase {
  Product createProduct(ProductCreateCommand command);
}
