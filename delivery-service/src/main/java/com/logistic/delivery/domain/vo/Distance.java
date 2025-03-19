package com.logistic.delivery.domain.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Distance {
  private Float expected;
  private Float actual;

  public Distance(Float expected) {
    this.expected = expected;
  }

  public Distance(Float actual, Float expected) {
    this.actual = actual;
    this.expected = expected;
  }

  public void updateActualDistance(Float actual){
    this.actual = actual;
  }
}
