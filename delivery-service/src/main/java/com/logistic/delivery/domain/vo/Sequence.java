package com.logistic.delivery.domain.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sequence {
  private Integer current;
  private Integer end;

  public Sequence(Integer current, Integer end) {
    this.current = current;
    this.end = end;
  }
}
