package com.logistic.delivery.adaptor.out.persistence;

import com.logistic.delivery.adaptor.out.persistence.value.DistanceValue;
import com.logistic.delivery.adaptor.out.persistence.value.TimeValue;
import com.logistic.delivery.domain.DeliveryStatus;
import com.logistic.delivery.domain.vo.Sequence;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubDeliveryHistoryValue {

  @Embedded
  @Column(name = "sequence", nullable = false)
  private Sequence sequence;

  @Column(name = "depart_hub_id", nullable = false)
  private Long departHubId;

  @Column(name = "arrival_hub_id", nullable = false)
  private Long arrivalHubId;

  @Embedded
  @Column(name = "time", nullable = false)
  private TimeValue time;

  @Embedded
  @Column(name = "distance", nullable = false)
  private DistanceValue distance;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private DeliveryStatus status;

  @Column(name = "driver_id", nullable = false)
  private String driverId;

}
