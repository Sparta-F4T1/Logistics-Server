package com.logistic.hub.adapter.in.web;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.hub.adapter.in.web.mapper.RouteWebMapper;
import com.logistic.hub.adapter.in.web.request.RouteCreateRequest;
import com.logistic.hub.adapter.in.web.response.RouteCreateResponse;
import com.logistic.hub.application.port.in.RouteUseCase;
import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.domain.Route;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hubRoutes")
public class RouteWebAdaptor {
  private final RouteUseCase routeUseCase;
  private final RouteWebMapper routeWebMapper;

  @PostMapping
  public ResponseEntity<ApiResponse<RouteCreateResponse>> createHubRoute(
      @Valid @RequestBody RouteCreateRequest request) {
    RouteCreateCommand command = routeWebMapper.toRouteCreateCommand(request);
    Route route = routeUseCase.createOrUpdateHubRoute(command);
    RouteCreateResponse routeResponse = routeWebMapper.toRouteCreateResponse(route);
    ApiResponse<RouteCreateResponse> response = ApiResponse.success(routeResponse);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }


  @DeleteMapping("/{hubRouteId}")
  public ResponseEntity<ApiResponse<String>> deleteHubRoute(@PathVariable Long hubRouteId) {

    routeUseCase.deleteHubRoute(hubRouteId);

    ApiResponse<String> response = ApiResponse.success("삭제되었습니다");
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
