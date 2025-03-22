package com.logistic.delivery.domain.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Distance {
  private Double expected;
  private Double actual;

  public Distance(Double expected) {
    this.expected = expected;
  }

  public Distance(Double expected, Double actual) {
    this.expected = expected;
    this.actual = actual;
  }

  public void updateActualDistance(Double actual){
    this.actual = actual;
  }
}
