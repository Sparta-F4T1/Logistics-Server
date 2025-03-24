package com.logistic.order.application.port.in.command;

import com.logistic.common.passport.model.UserInfo;
import java.util.List;

public record CreateOrderCommand(
    Long sellerId,
    Long buyerId,
    String memo,
    List<OrderProduct> orderProducts,
    UserInfo userInfo
) {
  public record OrderProduct(
      Long productId,
      int quantity
  ) {
  }
}
