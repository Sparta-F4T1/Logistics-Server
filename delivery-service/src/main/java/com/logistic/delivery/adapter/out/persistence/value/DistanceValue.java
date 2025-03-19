package com.logistic.delivery.adapter.out.persistence.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DistanceValue {
  @Column(name = "expected_dist", nullable = false)
  private Float expectedDist;

  @Column(name = "actual_dist")
  private Float actualDist;

  public DistanceValue(Float expectedDist) {
    this.expectedDist = expectedDist;
  }

  public DistanceValue(Float expectedDist, Float actualDist) {
    this.expectedDist = expectedDist;
    this.actualDist = actualDist;
  }
}
