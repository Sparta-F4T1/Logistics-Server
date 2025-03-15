package com.logistic.product.domain;

import com.logistic.product.domain.vo.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Product {
  private Long id;
  private String name;
  private Stock stock;
  private Long hubId;
  private Boolean isDeleted;

  public static Product create(final String name, final Integer stock, final Long hubId) {
    return Product.builder()
        .name(name)
        .stock(new Stock(stock))
        .hubId(hubId)
        .isDeleted(false)
        .build();
  }

}
