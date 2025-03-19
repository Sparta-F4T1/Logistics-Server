package com.logistic.hub.adapter.in.internal.response;

import com.logistic.hub.domain.Route;
import java.util.List;

public record HubClientShortestPathResponse(
    List<Route> path,
    Integer distance,
    Integer duration
) {
  public static HubClientShortestPathResponse from(List<Route> path, Integer distance, Integer duration) {
    return new HubClientShortestPathResponse(
        path, distance, duration
    );
  }
}
