package com.logistic.order.adapter.in.external.web.response;

import com.logistic.order.domain.vo.Product;
import java.util.List;

public record CreateOrderResponse(
    Long id,
    Long sellerId,
    Long buyerId,
    String memo,
    String status,
    List<OrderProduct> orderProducts) {

  public record OrderProduct(
      Product product,
      Integer quantity) {
  }
}
