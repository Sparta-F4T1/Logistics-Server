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
  private Product product;
  private int quantity;

  public static OrderProduct create(Long productId, String productName, int quantity) {
    return OrderProduct.builder()
        .product(Product.create(productId, productName))
        .quantity(quantity)
        .build();
  }

  public Long getProductId() {
    return product.getProductId();
  }

}
