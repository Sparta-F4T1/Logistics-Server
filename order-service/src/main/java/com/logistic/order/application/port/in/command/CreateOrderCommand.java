package com.logistic.order.application.port.in.command;

import java.util.List;

public record CreateOrderCommand(
    Long sellerId,
    Long buyerId,
    String memo,
    List<OrderProduct> orderProducts
) {
  public record OrderProduct(
      Long productId,
      int quantity
  ) {
  }
}
