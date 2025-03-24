package com.logistic.product.application.port.in;

import com.logistic.product.application.port.in.command.CreateProductCommand;
import com.logistic.product.application.port.in.command.DeleteProductCommand;
import com.logistic.product.application.port.in.command.UpdateProductCommand;
import com.logistic.product.application.port.in.command.UpdateStockCommand;
import com.logistic.product.domain.Product;

public interface ProductCommandUseCase {
  Product createProduct(CreateProductCommand command);

  Product updateProduct(UpdateProductCommand command);

  void deleteProduct(DeleteProductCommand command);

  void decreaseStock(UpdateStockCommand command);

  void increaseStock(UpdateStockCommand command);
}
