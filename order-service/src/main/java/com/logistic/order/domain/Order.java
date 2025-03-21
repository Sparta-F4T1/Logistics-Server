package com.logistic.order.domain;

import com.logistic.order.domain.vo.OrderProduct;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
  private Long id;

  private Long sellerId;

  private Long buyerId;

  private String memo;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  private List<OrderProduct> orderProducts;

  public static Order create(Long sellerId, Long buyerId, String memo, OrderStatus orderStatus, List<OrderProduct> orderProducts) {
    return Order.builder()
        .sellerId(sellerId)
        .buyerId(buyerId)
        .memo(memo)
        .status(orderStatus)
        .orderProducts(orderProducts)
        .build();
  }

  public void updateStatus(OrderStatus orderStatus) {
    this.status = orderStatus;
  }
}
