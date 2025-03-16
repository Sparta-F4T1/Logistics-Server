package com.logistic.product.domain.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {
  private Integer quantity;

  public Stock(final Integer quantity) {
    validate(quantity);
    this.quantity = quantity;
  }

  public Stock update(final Integer newQuantity) {
    return new Stock(newQuantity);
  }

  public Stock add(final Integer addQuantity) {
    return new Stock(quantity + addQuantity);
  }

  public Stock decrease(final Integer decreaseQuantity) {
    return new Stock(quantity - decreaseQuantity);
  }

  private void validate(final Integer quantity) {
    // todo 재고 예외처리 수정
    if (quantity == null) {
      throw new IllegalArgumentException("재고는 null일 수 없습니다.");
    }
    if (quantity < 0) {
      throw new IllegalArgumentException("재고는 음수일 수 없습니다.");
    }
  }
}
