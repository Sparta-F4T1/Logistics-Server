package com.logistic.product.adaptor.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockValue {

  @Column(name = "stock", nullable = false)
  private Integer stock;

  public StockValue(final Integer stock) {
    this.stock = stock;
  }
}
