package com.logistic.gps.domain;

import com.logistic.gps.adapter.out.external.response.NaverGeoAddressDetail;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Gps {
  private Long gpsId;
  private String road;
  private String jibun;
  private Double latitude;
  private Double longitude;

  public static Gps createGps(NaverGeoAddressDetail gpsInfoResponse) {

    return Gps.builder().
        road(gpsInfoResponse.roadAddress())
        .jibun(gpsInfoResponse.jibunAddress())
        .latitude(gpsInfoResponse.x())
        .longitude(gpsInfoResponse.y())
        .build();
  }
}
