package com.logistic.hub.adaptor.out.persistence;

import jakarta.persistence.Column;
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
  @Column(name = "road", nullable = false)
  private String road;

  @Column(name = "jibun", nullable = false)
  private String jibun;

  @Column(name = "latitude", nullable = false)
  private Double latitude;

  @Column(name = "longitude", nullable = false)
  private Double longitude;

  public AddressValue(String road, String jibun, Double latitude, Double longitude) {
    this.road = road;
    this.jibun = jibun;
    this.latitude = latitude;
    this.longitude = longitude;
  }
}