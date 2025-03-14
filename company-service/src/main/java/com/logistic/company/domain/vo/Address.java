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

  public Address(final String road, final String jibun, final Double latitude, final Double longitude) {
    validate(road, jibun, latitude, longitude);
    this.road = road;
    this.jibun = jibun;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  private void validate(final String road, final String jibun, final Double latitude, final Double longitude) {
    validateNotEmpty(road, "road");
    validateNotEmpty(jibun, "jibun");
    validateLatitude(latitude);
    validateLongitude(longitude);
  }

  private void validateNotEmpty(final String value, final String fieldName) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(fieldName + "는 비워둘 수 없습니다.");
    }
  }

  private void validateLatitude(final Double latitude) {
    if (latitude == null || latitude < -90 || latitude > 90) {
      throw new IllegalArgumentException("위도(latitude)는 -90 ~ 90 범위여야 합니다.");
    }
  }

  private void validateLongitude(final Double longitude) {
    if (longitude == null || longitude < -180 || longitude > 180) {
      throw new IllegalArgumentException("경도(longitude)는 -180 ~ 180 범위여야 합니다.");
    }
  }
}
