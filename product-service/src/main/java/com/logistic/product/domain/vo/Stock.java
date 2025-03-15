package com.logistic.product.domain.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {
  private Integer stock;

  public Stock(final Integer stock) {
    validate(stock);
    this.stock = stock;
  }

  private void validate(final Integer stock) {
    if (stock == null) {
      throw new IllegalArgumentException("재고는 null일 수 없습니다.");
    }
    if (stock < 0) {
      throw new IllegalArgumentException("재고는 음수일 수 없습니다.");
    }
  }
}
