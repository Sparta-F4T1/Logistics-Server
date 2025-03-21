package com.logistic.hub.adapter.in.web;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.hub.adapter.in.web.mapper.RouteWebMapper;
import com.logistic.hub.adapter.in.web.response.RouteDetailsResponse;
import com.logistic.hub.adapter.in.web.response.RouteHistoryListResponse;
import com.logistic.hub.application.port.in.RouteQueryUseCase;
import com.logistic.hub.application.service.dto.RouteDetailsDto;
import com.logistic.hub.application.service.dto.RouteHistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hubRoutes")
public class RouteQueryAdaptor {
  private final RouteQueryUseCase routeQueryUseCase;
  private final RouteWebMapper routeWebMapper;

  @GetMapping
  public ResponseEntity<ApiResponse<RouteHistoryListResponse>> getHubRouteList(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "searchType", defaultValue = "departHubName") String searchType,
      @RequestParam(value = "search", required = false) String search) {
    Page<RouteHistoryDto> hubHistory = routeQueryUseCase.getHubRouteList(
        routeWebMapper.toSearchQuery(page, size, searchType, search));

    RouteHistoryListResponse routeList = RouteHistoryListResponse.from(hubHistory);

    ApiResponse<RouteHistoryListResponse> response = ApiResponse.success(routeList);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping("/{hubRouteId}")
  public ResponseEntity<ApiResponse<RouteDetailsResponse>> getRouteDetails(@PathVariable Long hubRouteId) {
    RouteDetailsDto routeDetails = routeQueryUseCase.getRouteDetails(routeWebMapper.toFindQuery(hubRouteId));

    ApiResponse<RouteDetailsResponse> response = ApiResponse.success(
        routeWebMapper.toRouteDetailsResponse(routeDetails));
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

}
