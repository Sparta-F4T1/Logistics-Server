package com.logistic.hub.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Route {

  private Long routeId;
  private Long departHubId;
  private Long arrivalHubId;
  private Integer distance;
  private Integer duration;

}
