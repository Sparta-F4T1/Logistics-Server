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
public class Product{
    private Long productId;
    private String productName;

  public static Product create(Long productId, String productName) {
    return Product.builder()
        .productId(productId)
        .productName(productName)
        .build();
  }
}
