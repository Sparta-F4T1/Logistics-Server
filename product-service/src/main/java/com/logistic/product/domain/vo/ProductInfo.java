package com.logistic.product.domain.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductInfo {
  private String name;
  private Long companyId;

  public ProductInfo(final String name, final Long companyId) {
    validate(name, companyId);
    this.name = name;
    this.companyId = companyId;
  }

  public ProductInfo update(final String name) {
    return new ProductInfo(name, companyId);
  }

  private void validate(final String name, final Long companyId) {
    validateName(name);
    validateCompanyId(companyId);
  }

  private void validateName(final String name) {
    if (name == null || name.isEmpty()) {
      // todo 예외 구현
    }
  }

  private void validateCompanyId(final Long companyId) {
    if (companyId == null || companyId < 0) {
      // todo 예외 구현
    }
  }
}
