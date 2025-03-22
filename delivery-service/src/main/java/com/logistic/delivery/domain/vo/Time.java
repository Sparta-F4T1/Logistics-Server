package com.logistic.delivery.domain.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Time {
  private Integer expected;
  private Integer actual;

  public Time(Integer expected) {
    this.expected = expected;
  }

  public Time(Integer expected, Integer actual) {
    this.expected = expected;
    this.actual = actual;
  }

  public void updateActualTime(Integer actual){
    this.actual = actual;
  }
}
