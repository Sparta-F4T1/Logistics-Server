package com.logistic.delivery.adaptor.out.persistence.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SequenceValue {
  @Column(name = "sequence", nullable = false)
  private Integer current;

  @Column(name = "end_sequence", nullable = false)
  private Integer end;

  public SequenceValue(Integer current, Integer end) {
    this.current = current;
    this.end = end;
  }
}
