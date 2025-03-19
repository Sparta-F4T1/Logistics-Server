package com.logistic.order.adaptor.in.web.response;

import com.logistic.common.internal.response.OrderClientResponse;
import java.util.List;

public record CreateOrderResponse(
    Long orderId,
    Long sellerId,
    Long buyerId,
    String memo,
    String status,
    List<OrderClientResponse.OrderProduct> orderProductList) {

  public record OrderProduct(
      Long productId,
      Integer quantity) {
  }
}
