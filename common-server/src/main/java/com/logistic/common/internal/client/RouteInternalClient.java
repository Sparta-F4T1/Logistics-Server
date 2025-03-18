package com.logistic.common.internal.client;

import com.logistic.common.internal.request.RouteClientRequest;
import com.logistic.common.internal.response.RouteClientResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface RouteInternalClient {
  @GetMapping("/internal/v1/routes/{routeId}")
  RouteClientResponse findRoute(@PathVariable("routeId") Long routeId, @RequestBody RouteClientRequest request);

  @GetMapping("/internal/v1/routes")
  List<RouteClientResponse> findRouteList(@RequestBody RouteClientRequest request);
}
