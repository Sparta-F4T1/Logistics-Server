package com.logistic.product.application.port.in.command;

import com.logistic.common.passport.model.Passport;
import com.logistic.product.domain.command.ProductForCreate;
import com.logistic.product.domain.vo.Company;

public record CreateProductCommand(
    String name,
    Integer quantity,
    Long companyId,
    Passport passport) {
  public ProductForCreate toForCreate(final Company company) {
    return new ProductForCreate(name, quantity, company);
  }
}
