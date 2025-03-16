package com.logistic.hub.domain;

import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.domain.command.RouteInfoCommand;
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

  public static Route createRoute(RouteCreateCommand routeCreateCommand, RouteInfoCommand routeInfoCommand) {

    return Route.builder()
        .departHubId(routeCreateCommand.departHubId())
        .arrivalHubId(routeCreateCommand.arrivalHubId())
        .distance(routeInfoCommand.distance())
        .duration(routeInfoCommand.duration())
        .build();
  }
}
