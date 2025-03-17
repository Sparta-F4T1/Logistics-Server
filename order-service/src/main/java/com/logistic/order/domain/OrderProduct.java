package com.logistic.order.domain;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {
  private Long id;
  private Long orderId;
  private Long productId;
  private int amount;

  public static OrderProduct create(Long productId, int amount) {
    return OrderProduct.builder()
        .productId(productId)
        .amount(amount)
        .build();
  }
}
