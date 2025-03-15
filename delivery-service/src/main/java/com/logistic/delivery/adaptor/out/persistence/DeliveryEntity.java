package com.logistic.delivery.adaptor.out.persistence;

import com.logistic.delivery.domain.DeliveryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "p_delivery")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "delivery_id")
  private Long id;

  @Column(name = "order_id", nullable = false)
  private Long orderId;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private DeliveryStatus status;

  @Column(name = "depart_hub_id", nullable = false)
  private Long departHubId;

  @Column(name = "arrival_hub_id", nullable = false)
  private Long arrivalHubId;

  @Column(name = "driver_id", nullable = false)
  private String driverId;

  @Override
  public String toString() {
    return "DeliveryEntity{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", status=" + status +
        ", departHubId=" + departHubId +
        ", arrivalHubId=" + arrivalHubId +
        ", driverId='" + driverId + '\'' +
        '}';
  }
}
