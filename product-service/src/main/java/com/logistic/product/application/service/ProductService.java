package com.logistic.product.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.product.application.port.in.ProductUseCase;
import com.logistic.product.application.port.in.command.ProductCreateCommand;
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
    final Product product = Product.create(command.name(), command.stock(), command.companyId());
    return productPersistencePort.save(product);
  }

  private void checkIfCompanyExists(final Long companyId) {
    final boolean companyExists = companyClientPort.existsById(companyId);
    if (!companyExists) {
      //todo 업체 존재 확인 예외처리
    }
  }
}
