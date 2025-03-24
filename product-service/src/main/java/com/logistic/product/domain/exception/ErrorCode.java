package com.logistic.product.domain.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

  HUB_NOT_FOUND("H001", "해당 허브가 존재하지 않습니다."),
  USER_NOT_FOUND("U001", "해당 유저가 존재하지 않습니다."),
  COMPANY_NOT_FOUND("C001", "해당 업체가 존재하지 않습니다."),
  PRODUCT_NOT_FOUND("P001", "해당 상품이 존재하지 않습니다."),

  CREATE_PRODUCT_FORBIDDEN("P002", "상품을 생성할 권한이 없습니다."),
  UPDATE_PRODUCT_FORBIDDEN("P003", "상품을 수정할 권한이 없습니다."),
  DELETE_PRODUCT_FORBIDDEN("P004", "상품을 삭제할 권한이 없습니다."),
  ACCESS_PRODUCT_FORBIDDEN("P005", "상품을 조회할 권한이 없습니다."),

  STOCK_NOT_AVAILABLE("P006", "재고가 부족합니다."),
  DECREASE_STOCK_ERROR("P007", "재고 차감 중 오류가 발생하였습니다.");

  private final String code;
  private final String message;

  ErrorCode(final String code, final String message) {
    this.code = code;
    this.message = message;
  }

}
