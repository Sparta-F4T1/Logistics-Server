package com.logistic.order.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
  private Long id;
  private Long sellerId;
  private Long buyerId;
  private String memo;
  private OrderStatus status;

  public static Order create(Long sellerId, Long buyerId, String memo, Long productId, int amount){
    return Order.builder()
        .sellerId(sellerId)
        .buyerId(buyerId)
        .memo(memo)
        .status(OrderStatus.PENDING)
        .build();
  }
}
