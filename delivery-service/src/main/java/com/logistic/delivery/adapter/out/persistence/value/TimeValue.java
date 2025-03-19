package com.logistic.delivery.adapter.out.persistence.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeValue {
  @Column(name = "expected_time", nullable = false)
  private Integer expectedTime;

  @Column(name = "actual_time")
  private Integer actualTime;

  public TimeValue(Integer expectedTime) {
    this.expectedTime = expectedTime;
  }

  public TimeValue(Integer expectedTime, Integer actualTime) {
    this.expectedTime = expectedTime;
    this.actualTime = actualTime;
  }
}
