package com.logistic.common.internal.response;

import java.util.List;

public record OrderClientResponse(
    Long orderId,
    Long sellerId,
    Long buyerId,
    String memo,
    String status,
    List<OrderProduct> orderProductList) {

  public record OrderProduct(
      Long productId,
      Integer quantity) {
  }

}
