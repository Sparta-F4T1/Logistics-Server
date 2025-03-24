package com.logistic.product.domain;

import com.logistic.product.domain.command.ProductForCreate;
import com.logistic.product.domain.command.ProductForUpdate;
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
  private Long companyId;
  private Stock stock;
  private Boolean isDeleted;

  public static Product create(final ProductForCreate forCreate) {
    return Product.builder()
        .name(forCreate.name())
        .companyId(forCreate.company().companyId())
        .stock(new Stock(forCreate.quantity()))
        .isDeleted(false)
        .build();
  }

  public void update(final ProductForUpdate forUpdate) {
    this.name = forUpdate.name();
    this.stock = stock.update(forUpdate.quantity());
  }

  public void decreaseStock(final Integer quantity) {
    this.stock = stock.decrease(quantity);
  }

  public void increaseStock(final Integer quantity) {
    this.stock = stock.increase(quantity);
  }

  public void delete() {
    this.isDeleted = true;
  }
}
