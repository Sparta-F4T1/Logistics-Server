package com.logistic.product.domain.exception;

import lombok.Getter;

@Getter
public enum DomainErrorCode {

  PRODUCT_NOT_FOUND("P001", "해당 상품이 존재하지 않습니다."),
  COMPANY_NOT_FOUND("C001", "해당 업체가 존재하지 않습니다.");

  private final String code;
  private final String message;

  DomainErrorCode(final String code, final String message) {
    this.code = code;
    this.message = message;
  }

}
