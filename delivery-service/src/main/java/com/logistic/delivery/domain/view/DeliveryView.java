package com.logistic.delivery.domain.view;

import com.logistic.delivery.domain.DeliveryStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryView {
  private Long id;
  private Long orderId;
  private DeliveryStatus status;
  private Long departCompanyId;
  private Long arrivalCompanyId;
  private Long departHubId;
  private Long arrivalHubId;
  private String driverId;
  private List<HubDeliveryHistoryView> hubDeliveryHistories;

  public static DeliveryView create(
      Long orderId,
      DeliveryStatus status,
      Long departCompanyId,
      Long arrivalCompanyId,
      Long departHubId,
      Long arrivalHubId,
      List<HubDeliveryHistoryView> hubDeliveryHistoriesHistories
  ){
    return DeliveryView.builder()
        .orderId(orderId)
        .status(status)
        .departCompanyId(departCompanyId)
        .arrivalCompanyId(arrivalCompanyId)
        .departHubId(departHubId)
        .arrivalHubId(arrivalHubId)
        .hubDeliveryHistories(hubDeliveryHistoriesHistories)
        .build();
  }

}
