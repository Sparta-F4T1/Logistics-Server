package com.logistic.company.domain.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
  private String road;
  private String jibun;
  private Double latitude;
  private Double longitude;

  public Address(String road, String jibun, Double latitude, Double longitude) {
    this.road = road;
    this.jibun = jibun;
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
