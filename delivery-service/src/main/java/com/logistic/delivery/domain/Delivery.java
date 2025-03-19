package com.logistic.delivery.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
  private Long id;
  private Long orderId;
  private DeliveryStatus status;
  private Long departCompanyId;
  private Long arrivalCompanyId;
  private Long departHubId;
  private Long arrivalHubId;
  private String driverId;
  private List<HubDeliveryHistory> hubHistories;

  public static Delivery create(
      Long orderId,
      DeliveryStatus status,
      Long departCompanyId,
      Long arrivalCompanyId,
      Long departHubId,
      Long arrivalHubId,
      List<HubDeliveryHistory> hubHistories
  ){
    return Delivery.builder()
        .orderId(orderId)
        .status(status)
        .departCompanyId(departCompanyId)
        .arrivalCompanyId(arrivalCompanyId)
        .departHubId(departHubId)
        .arrivalHubId(arrivalHubId)
        .hubHistories(hubHistories)
        .build();
  }

  public void updateStatus(String status) {
    this.status = DeliveryStatus.valueOf(status);
  }

  public void updateDriverId(String driverId) {
    this.driverId = driverId;
  }

}
