package com.logistic.product.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.common.passport.model.Passport;
import com.logistic.product.application.port.in.ProductCommandUseCase;
import com.logistic.product.application.port.in.command.AddStockCommand;
import com.logistic.product.application.port.in.command.CreateProductCommand;
import com.logistic.product.application.port.in.command.DecreaseStockCommand;
import com.logistic.product.application.port.in.command.DeleteProductCommand;
import com.logistic.product.application.port.in.command.UpdateProductCommand;
import com.logistic.product.application.port.out.ProductInternalPort;
import com.logistic.product.application.port.out.ProductPersistencePort;
import com.logistic.product.application.service.dto.CompanyInfo;
import com.logistic.product.domain.Product;
import com.logistic.product.domain.command.ProductForCreate;
import com.logistic.product.domain.command.ProductForUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class ProductCommandService implements ProductCommandUseCase {
  private final ProductPersistencePort productPersistencePort;
  private final ProductInternalPort productInternalPort;

  @Override
  public Product createProduct(final CreateProductCommand command) {
    final CompanyInfo companyInfo = findCompany(command.companyId(), command.passport());
    final ProductForCreate forCreate = new ProductForCreate(command.name(), command.quantity(),
        companyInfo.companyId());
    final Product product = Product.create(forCreate);
    return productPersistencePort.save(product);
  }

  @Override
  public Product updateProduct(final UpdateProductCommand command) {
    Product product = findProduct(command.productId());
    final ProductForUpdate forUpdate = new ProductForUpdate(command.name(), command.quantity());
    product.updateInfo(forUpdate);
    return productPersistencePort.save(product);
  }

  @Override
  public Product addStock(final AddStockCommand command) {
    Product product = findProduct(command.productId());
    product.addStock(command.quantity());
    return productPersistencePort.save(product);
  }

  @Override
  public Product decreaseStock(final DecreaseStockCommand command) {
    Product product = findProduct(command.productId());
    product.decreaseStock(command.quantity());
    return productPersistencePort.save(product);
  }

  @Override
  public void deleteProduct(final DeleteProductCommand command) {
    Product product = findProduct(command.productId());
    product.delete();
    productPersistencePort.save(product);
  }

  private Product findProduct(final Long productId) {
    return productPersistencePort.findById(productId);
  }

  private CompanyInfo findCompany(final Long companyId, Passport passport) {
    return productInternalPort.findCompany(companyId, passport);
  }
}
