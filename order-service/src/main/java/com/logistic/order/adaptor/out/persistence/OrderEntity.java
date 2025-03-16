package com.logistic.order.adaptor.out.persistence;

import com.logistic.order.domain.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "p_order")
@Entity
public class OrderEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long id;

  @Column(name = "seller_id", nullable = false)
  private Long sellerId;

  @Column(name = "buyer_id", nullable = false)
  private Long buyerId;

  @Column(name = "memo", nullable = false)
  private String memo;

  @Column(name = "status", nullable = false)
  private OrderStatus status;

}
