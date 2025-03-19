package com.logistic.hub.domain;

import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.application.port.in.command.RouteInfoCommand;
import com.logistic.hub.domain.exception.RouteInvalidInfoException;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Route {

  private Long id;
  private Long departHubId;
  private Long arrivalHubId;
  private Integer distance;
  private Integer duration;
  private Boolean isDeleted;

  public static Route createRoute(RouteCreateCommand routeCreateCommand, RouteInfoCommand routeInfoCommand) {
    if (routeInfoCommand.distance() < 0 || routeInfoCommand.duration() < 0) {
      throw new RouteInvalidInfoException("경로의 거리 혹은 소요시간은 0 이상이어야 합니다.");
    }
    return Route.builder()
        .departHubId(routeCreateCommand.departHubId())
        .arrivalHubId(routeCreateCommand.arrivalHubId())
        .distance(routeInfoCommand.distance())
        .duration(routeInfoCommand.duration())
        .isDeleted(false)
        .build();
  }

  public void update(RouteInfoCommand routeInfoCommand) {
    this.distance = routeInfoCommand.distance();
    this.duration = routeInfoCommand.duration();
  }
}
