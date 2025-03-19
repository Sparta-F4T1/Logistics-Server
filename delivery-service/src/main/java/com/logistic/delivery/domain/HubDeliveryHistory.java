package com.logistic.delivery.domain;

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
public class HubDeliveryHistory {
  private Long id;
  private Sequence sequence;
  private Long departHubId;
  private Long arrivalHubId;
  private Time time;
  private Distance distance;
  private DeliveryStatus status;
  private String driverId;

  public static HubDeliveryHistory create(
      Sequence sequence,
      Long departHubId,
      Long arrivalHubId,
      Time time,
      Distance distance,
      DeliveryStatus status,
      String driverId
  ){
    return HubDeliveryHistory.builder()
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
