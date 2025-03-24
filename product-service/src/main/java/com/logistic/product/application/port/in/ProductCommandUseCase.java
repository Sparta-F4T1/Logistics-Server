package com.logistic.product.application.port.in;

import com.logistic.product.application.port.in.command.CreateProductCommand;
import com.logistic.product.application.port.in.command.DeleteCompanyCommand;
import com.logistic.product.application.port.in.command.DeleteProductCommand;
import com.logistic.product.application.port.in.command.UpdateProductCommand;
import com.logistic.product.application.port.in.command.UpdateStockCommand;
import com.logistic.product.domain.Product;
import java.util.List;

public interface ProductCommandUseCase {
  Product createProduct(CreateProductCommand command);

  Product updateProduct(UpdateProductCommand command);

  void deleteProduct(DeleteProductCommand command);

  List<Product> decreaseStock(UpdateStockCommand command);

  void increaseStock(UpdateStockCommand command);

  void deleteProductByCompany(DeleteCompanyCommand command);
}
