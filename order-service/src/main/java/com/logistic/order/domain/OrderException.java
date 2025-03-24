package com.logistic.order.domain;

import com.logistic.common.response.code.ErrorCode;
import org.springframework.http.HttpStatus;

public class OrderException {

  public static class OrderNotFoundException extends BaseException {
    public OrderNotFoundException() {
      super("해당 주문 정보가 없습니다.", ErrorCode.ENTITY_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
  }

  public static class OrderIsDeletedException extends BaseException {
    public OrderIsDeletedException() {
      super("해당 주문 정보가 삭제되었습니다.", ErrorCode.ENTITY_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
  }

  public static class OrderBuyerNotAuthorized extends BaseException {
    public OrderBuyerNotAuthorized() {
      super("해당 구매 업체에 대한 권한이 없는 사용자입니다.", ErrorCode.HANDLE_ACCESS_DENIED, HttpStatus.UNAUTHORIZED);
    }
  }
}
