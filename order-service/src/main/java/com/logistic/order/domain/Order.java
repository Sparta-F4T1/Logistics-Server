package com.logistic.order.domain;

import com.logistic.order.domain.vo.Company;
import com.logistic.order.domain.vo.OrderProduct;
import com.logistic.order.domain.vo.User;
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

  private Company seller;

  private Company buyer;

  private String memo;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  private List<OrderProduct> orderProducts;

  private User user;

  public static Order create(Company seller, Company buyerId, String memo, OrderStatus orderStatus, List<OrderProduct> orderProducts, String userId, String userName) {
    return Order.builder()
        .seller(seller)
        .buyer(buyerId)
        .memo(memo)
        .status(orderStatus)
        .orderProducts(orderProducts)
        .user(User.create(userId, userName))
        .build();
  }

  public void updateStatus(OrderStatus orderStatus) {
    this.status = orderStatus;
  }
}
