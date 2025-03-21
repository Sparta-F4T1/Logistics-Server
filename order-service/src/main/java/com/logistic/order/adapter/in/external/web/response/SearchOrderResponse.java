package com.logistic.order.adapter.in.external.web.response;

import java.util.List;

public record SearchOrderResponse(
    Long id,
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
