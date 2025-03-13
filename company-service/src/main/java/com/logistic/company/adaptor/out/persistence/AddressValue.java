package com.logistic.company.adaptor.out.persistence;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class AddressValue {
  private String road;
  private String jibun;
  private Double latitude;
  private Double longitude;

  public AddressValue(String road, String jibun, Double latitude, Double longitude) {
    this.road = road;
    this.jibun = jibun;
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
