package com.logistic.order.adapter.out.persistence;

import com.logistic.order.domain.OrderStatus;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private OrderStatus status;

  @ElementCollection
  @CollectionTable(
      name = "p_order_product",
      joinColumns = @JoinColumn(name = "order_id")
  )
  private List<OrderProductValue> orderProducts;

}
