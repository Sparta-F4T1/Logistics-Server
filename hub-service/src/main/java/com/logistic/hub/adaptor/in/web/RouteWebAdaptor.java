package com.logistic.hub.adaptor.in.web;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.hub.adaptor.in.web.mapper.RouteWebMapper;
import com.logistic.hub.adaptor.in.web.request.RouteCreateRequest;
import com.logistic.hub.adaptor.in.web.response.RouteCreateResponse;
import com.logistic.hub.adaptor.in.web.response.RouteDetailsResponse;
import com.logistic.hub.adaptor.in.web.response.RouteHistoryListResponse;
import com.logistic.hub.application.port.in.RouteUseCase;
import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.domain.Route;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping
  public ResponseEntity<ApiResponse<RouteHistoryListResponse>> getHubRouteList(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "sortBy", defaultValue = "departHubName") String searchType,
      @RequestParam(value = "search", required = false) String search) {

    RouteHistoryListResponse hubHistoryResponse = routeUseCase.getHubRouteList(page, size, searchType, search);

    ApiResponse<RouteHistoryListResponse> response = ApiResponse.success(hubHistoryResponse);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping("/{hubRouteId}")
  public ResponseEntity<ApiResponse<RouteDetailsResponse>> getRouteDetails(@PathVariable Long hubRouteId) {
    RouteDetailsResponse routeDetails = routeUseCase.getRouteDetails(hubRouteId);

    ApiResponse<RouteDetailsResponse> response = ApiResponse.success(routeDetails);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @DeleteMapping("/{hubRouteId}")
  public ResponseEntity<ApiResponse<String>> deleteHubRoute(@PathVariable Long hubRouteId) {

    routeUseCase.deleteHubRoute(hubRouteId);

    ApiResponse<String> response = ApiResponse.success("삭제되었습니다");
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
