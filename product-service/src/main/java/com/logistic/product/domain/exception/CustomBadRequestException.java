package com.logistic.product.domain.exception;

import static com.logistic.product.domain.exception.ErrorCode.DECREASE_STOCK_ERROR;
import static com.logistic.product.domain.exception.ErrorCode.STOCK_NOT_AVAILABLE;

import lombok.Getter;

@Getter
public class CustomBadRequestException extends RuntimeException {

  private final String code;

  public CustomBadRequestException(String code, String message) {
    super(message);
    this.code = code;
  }

  public static class StockNotAvailableException extends CustomBadRequestException {
    public StockNotAvailableException() {
      super(STOCK_NOT_AVAILABLE.getCode(), STOCK_NOT_AVAILABLE.getMessage());
    }
  }

  public static class ProductLockException extends CustomBadRequestException {
    public ProductLockException(String message) {
      super(DECREASE_STOCK_ERROR.getCode(), message);
    }
  }
}