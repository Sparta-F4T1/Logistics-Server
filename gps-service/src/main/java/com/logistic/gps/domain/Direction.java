package com.logistic.gps.domain;

import com.logistic.gps.adapter.out.external.response.NaverDirectionResponse;
import com.logistic.gps.adapter.out.external.response.RouteSummaryDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Direction {
  private Integer distance;
  private Integer duration;

  public static Direction createDirection(NaverDirectionResponse naverDirectionResponse) {
    RouteSummaryDto summary = naverDirectionResponse.route().traoptimal().get(0).summary();

    return Direction.builder()
        .distance(summary.distance())
        .duration(summary.duration())
        .build();
  }
}
