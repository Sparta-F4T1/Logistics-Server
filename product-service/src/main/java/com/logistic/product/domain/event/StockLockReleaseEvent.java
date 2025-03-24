package com.logistic.product.domain.event;

import java.util.List;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class StockLockReleaseEvent extends ApplicationEvent {
  private final List<Long> productIds;

  public StockLockReleaseEvent(Object source, List<Long> productIds) {
    super(source);
    this.productIds = productIds;
  }

}
