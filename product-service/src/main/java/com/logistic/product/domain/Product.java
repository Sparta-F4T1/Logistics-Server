package com.logistic.product.domain;

import com.logistic.product.domain.vo.ProductInfo;
import com.logistic.product.domain.vo.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Product {
  private Long id;
  private ProductInfo info;
  private Stock stock;
  private Boolean isDeleted;

  public static Product create(final String name, final Integer stock, final Long companyId) {
    return Product.builder()
        .info(new ProductInfo(name, companyId))
        .stock(new Stock(stock))
        .isDeleted(false)
        .build();
  }

  public void updateInfo(final String name) {
    this.info = info.update(name);
  }

  public void updateStock(final Integer newStock) {
    this.stock = stock.update(newStock);
  }

  public void addStock(final Integer quantity) {
    this.stock = stock.add(quantity);
  }

  public void decreaseStock(final Integer quantity) {
    this.stock = stock.decrease(quantity);
  }

  public void softDelete() {
    if (this.isDeleted) {
      // todo 이미 삭제된 경우 예외 처리
    }
    this.isDeleted = true;
  }
}
