package com.logistic.hub.adaptor.in.web;

import com.logistic.common.annotation.WebAdapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.hub.adaptor.in.web.mapper.RouteWebMapper;
import com.logistic.hub.adaptor.in.web.request.RouteCreateRequest;
import com.logistic.hub.adaptor.in.web.response.RouteCreateResponse;
import com.logistic.hub.application.port.in.RouteUseCase;
import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.domain.Route;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/v1/hubRoutes")
public class RouteWebAdaptor {
  private final RouteUseCase routeUseCase;
  private final RouteWebMapper routeWebMapper;

  @PostMapping
  public ResponseEntity<ApiResponse<RouteCreateResponse>> createHubRoute(
      @Valid @RequestBody RouteCreateRequest request) {
    RouteCreateCommand command = routeWebMapper.toRouteCreateCommand(request);
    Route route = routeUseCase.createHubRoute(command);
    RouteCreateResponse routeResponse = routeWebMapper.toRouteCreateResponse(route);
    ApiResponse<RouteCreateResponse> response = ApiResponse.success(routeResponse);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
