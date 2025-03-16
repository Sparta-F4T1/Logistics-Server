package com.logistic.product.application.port.in;

import com.logistic.product.application.port.in.command.ProductCreateCommand;
import com.logistic.product.application.port.in.command.ProductDeleteCommand;
import com.logistic.product.application.port.in.command.ProductInfoUpdateCommand;
import com.logistic.product.application.port.in.command.StockAddCommand;
import com.logistic.product.application.port.in.command.StockDecreaseCommand;
import com.logistic.product.application.port.in.command.StockUpdateCommand;
import com.logistic.product.domain.Product;

public interface ProductUseCase {
  Product createProduct(ProductCreateCommand command);

  Product updateProductInfo(ProductInfoUpdateCommand command);

  Product updateStock(StockUpdateCommand command);

  Product addStock(StockAddCommand command);

  Product decreaseStock(StockDecreaseCommand command);

  void deleteProduct(ProductDeleteCommand command);
}
