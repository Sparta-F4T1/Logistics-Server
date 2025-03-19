package com.logistic.delivery.adaptor.out.persistence;

import com.logistic.delivery.domain.DeliveryStatus;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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

  @Column(name = "depart_company_id", nullable = false)
  private Long departCompanyId;

  @Column(name = "arrival_company_id", nullable = false)
  private Long arrivalCompanyId;

  @Column(name = "depart_hub_id", nullable = false)
  private Long departHubId;

  @Column(name = "arrival_hub_id", nullable = false)
  private Long arrivalHubId;

  @Column(name = "driver_id")
  private String driverId;

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(
      name = "hub_delivery_histories",
      joinColumns = @JoinColumn(name = "delivery_id")
  )
  private List<HubDeliveryHistoryValue> hubHistories;

  @Override
  public String toString() {
    return "DeliveryEntity{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", status=" + status +
        ", departCompanyId=" + departCompanyId +
        ", arrivalCompanyId=" + arrivalCompanyId +
        ", departHubId=" + departHubId +
        ", arrivalHubId=" + arrivalHubId +
        ", driverId='" + driverId + '\'' +
        ", hubHistories=" + hubHistories +
        '}';
  }
}
