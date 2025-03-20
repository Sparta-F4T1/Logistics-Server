package com.logistic.product.domain.exception;

import static com.logistic.product.domain.exception.DomainErrorCode.PRODUCT_NOT_FOUND;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

  private final String code;

  public DomainException(String code, String message) {
    super(message);
    this.code = code;
  }

  public static class ProductNotFoundException extends DomainException {
    public ProductNotFoundException() {
      super(PRODUCT_NOT_FOUND.getCode(), PRODUCT_NOT_FOUND.getMessage());
    }
  }

  public static class CompanyNotFoundException extends DomainException {
    public CompanyNotFoundException() {
      super(PRODUCT_NOT_FOUND.getCode(), PRODUCT_NOT_FOUND.getMessage());
    }
  }
}

