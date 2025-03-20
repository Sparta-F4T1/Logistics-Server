package com.logistic.product.domain;

import com.logistic.product.domain.command.ProductForCreate;
import com.logistic.product.domain.command.ProductForUpdate;
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

  public static Product create(final ProductForCreate forCreate) {
    return Product.builder()
        .info(new ProductInfo(forCreate.name(), forCreate.companyId()))
        .stock(new Stock(forCreate.quantity()))
        .isDeleted(false)
        .build();
  }

  public void updateInfo(final ProductForUpdate forUpdate) {
    this.info = info.update(forUpdate.name());
    this.stock = stock.update(forUpdate.quantity());
  }

  public void addStock(final Integer quantity) {
    this.stock = stock.add(quantity);
  }

  public void decreaseStock(final Integer quantity) {
    this.stock = stock.decrease(quantity);
  }

  public void delete() {
    this.isDeleted = true;
  }
}
