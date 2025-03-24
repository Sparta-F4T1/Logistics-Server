package com.logistic.product.domain.vo;

import com.logistic.product.domain.exception.CustomBadRequestException.StockNotAvailableException;
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

  public Stock decrease(final Integer decreaseQuantity) {
    return new Stock(quantity - decreaseQuantity);
  }

  public Stock increase(final Integer addQuantity) {
    return new Stock(quantity + addQuantity);
  }

  private void validate(final Integer quantity) {
    if (quantity < 0) {
      throw new StockNotAvailableException();
    }
  }
}
