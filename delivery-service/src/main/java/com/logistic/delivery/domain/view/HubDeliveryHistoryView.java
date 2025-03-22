package com.logistic.delivery.domain.view;

import com.logistic.delivery.domain.DeliveryStatus;
import com.logistic.delivery.domain.vo.Distance;
import com.logistic.delivery.domain.vo.Sequence;
import com.logistic.delivery.domain.vo.Time;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HubDeliveryHistoryView {
  private Sequence sequence;
  private Long departHubId;
  private Long arrivalHubId;
  private Time time;
  private Distance distance;
  private DeliveryStatus status;
  private String driverId;

  public static HubDeliveryHistoryView create(
      Sequence sequence,
      Long departHubId,
      Long arrivalHubId,
      Time time,
      Distance distance,
      DeliveryStatus status,
      String driverId
  ) {
    return HubDeliveryHistoryView.builder()
        .sequence(sequence)
        .departHubId(departHubId)
        .arrivalHubId(arrivalHubId)
        .time(time)
        .distance(distance)
        .status(status)
        .driverId(driverId)
        .build();
  }

}
