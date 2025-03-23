package com.logistic.common.internal.client;

import com.logistic.common.internal.response.RouteClientResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface RouteInternalClient {
  @GetMapping("/internal/v1/routes/{routeId}")
  RouteClientResponse findRoute(@PathVariable("routeId") Long routeId);

  @GetMapping("/internal/v1/routes/shortestPath")
  List<RouteClientResponse> shortestPath(@RequestParam Long departHubId, @RequestParam Long arrivalHubId);
}
