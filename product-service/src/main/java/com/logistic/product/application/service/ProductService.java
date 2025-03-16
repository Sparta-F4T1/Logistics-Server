package com.logistic.product.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.product.application.port.in.ProductUseCase;
import com.logistic.product.application.port.in.command.ProductCreateCommand;
import com.logistic.product.application.port.in.command.ProductDeleteCommand;
import com.logistic.product.application.port.in.command.ProductInfoUpdateCommand;
import com.logistic.product.application.port.in.command.StockAddCommand;
import com.logistic.product.application.port.in.command.StockDecreaseCommand;
import com.logistic.product.application.port.in.command.StockUpdateCommand;
import com.logistic.product.application.port.out.CompanyClientPort;
import com.logistic.product.application.port.out.ProductPersistencePort;
import com.logistic.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {
  private final ProductPersistencePort productPersistencePort;
  private final CompanyClientPort companyClientPort;

  @Override
  public Product createProduct(final ProductCreateCommand command) {
    checkIfCompanyExists(command.companyId());
    final Product product = Product.create(command.name(), command.quantity(), command.companyId());
    return productPersistencePort.save(product);
  }

  @Override
  public Product updateProductInfo(final ProductInfoUpdateCommand command) {
    Product product = findOrElseThrow(command.productId());
    product.updateInfo(command.name());
    return productPersistencePort.save(product);
  }

  @Override
  public Product updateStock(final StockUpdateCommand command) {
    Product product = findOrElseThrow(command.productId());
    product.updateStock(command.quantity());
    return productPersistencePort.save(product);
  }

  @Override
  public Product addStock(final StockAddCommand command) {
    Product product = findOrElseThrow(command.productId());
    product.addStock(command.quantity());
    return productPersistencePort.save(product);
  }

  @Override
  public Product decreaseStock(final StockDecreaseCommand command) {
    Product product = findOrElseThrow(command.productId());
    product.decreaseStock(command.quantity());
    return productPersistencePort.save(product);
  }

  @Override
  public void deleteProduct(final ProductDeleteCommand command) {
    Product product = findOrElseThrow(command.productId());
    product.softDelete();
    productPersistencePort.save(product);
  }

  private Product findOrElseThrow(final Long productId) {
    return productPersistencePort.findById(productId)
        .orElseThrow(() -> new RuntimeException("Product not found"));
  }

  private void checkIfCompanyExists(final Long companyId) {
    final boolean companyExists = companyClientPort.existsById(companyId);
    if (!companyExists) {
      //todo 업체 존재 확인 예외처리
    }
  }
}
