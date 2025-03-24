package com.logistic.product.domain.exception;

import static com.logistic.product.domain.exception.ErrorCode.ACCESS_PRODUCT_FORBIDDEN;
import static com.logistic.product.domain.exception.ErrorCode.CREATE_PRODUCT_FORBIDDEN;
import static com.logistic.product.domain.exception.ErrorCode.DELETE_PRODUCT_FORBIDDEN;
import static com.logistic.product.domain.exception.ErrorCode.UPDATE_PRODUCT_FORBIDDEN;

import lombok.Getter;

@Getter
public class CustomForbiddenException extends RuntimeException {

  private final String code;

  public CustomForbiddenException(String code, String message) {
    super(message);
    this.code = code;
  }

  public static class CreateProductForbiddenException extends CustomForbiddenException {
    public CreateProductForbiddenException() {
      super(CREATE_PRODUCT_FORBIDDEN.getCode(), CREATE_PRODUCT_FORBIDDEN.getMessage());
    }
  }

  public static class UpdateProductForbiddenException extends CustomForbiddenException {
    public UpdateProductForbiddenException() {
      super(UPDATE_PRODUCT_FORBIDDEN.getCode(), UPDATE_PRODUCT_FORBIDDEN.getMessage());
    }
  }

  public static class DeleteProductForbiddenException extends CustomForbiddenException {
    public DeleteProductForbiddenException() {
      super(DELETE_PRODUCT_FORBIDDEN.getCode(), DELETE_PRODUCT_FORBIDDEN.getMessage());
    }
  }

  public static class AccessProductForbiddenException extends CustomForbiddenException {
    public AccessProductForbiddenException() {
      super(ACCESS_PRODUCT_FORBIDDEN.getCode(), ACCESS_PRODUCT_FORBIDDEN.getMessage());
    }
  }
}