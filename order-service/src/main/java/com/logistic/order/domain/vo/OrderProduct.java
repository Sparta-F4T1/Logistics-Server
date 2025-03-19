package com.logistic.order.domain.vo;


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
  private Long orderId;
  private Long productId;
  private int quantity;

  public static OrderProduct create(Long productId, int quantity) {
    return OrderProduct.builder()
        .productId(productId)
        .quantity(quantity)
        .build();
  }
}
