package com.logistic.hub.adapter.in.web;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.hub.adapter.in.web.mapper.HubWebMapper;
import com.logistic.hub.adapter.in.web.response.HubDetailsResponse;
import com.logistic.hub.adapter.in.web.response.HubHistoryListResponse;
import com.logistic.hub.application.port.in.HubQueryUseCase;
import com.logistic.hub.application.service.dto.HubHistoryDto;
import com.logistic.hub.domain.Hub;
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
@RequestMapping("/api/v1/hubs")
public class HubQueryAdaptor {
  private final HubQueryUseCase hubQueryUseCase;
  private final HubWebMapper hubWebMapper;

  @GetMapping
  public ResponseEntity<ApiResponse<HubHistoryListResponse>> getHubList(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "search", required = false) String search) {

    Page<HubHistoryDto> hubList = hubQueryUseCase.getHubList(
        hubWebMapper.toSearchQuery(page, size, search));
    HubHistoryListResponse hubHistoryResponse = HubHistoryListResponse.from(hubList);

    ApiResponse<HubHistoryListResponse> response = ApiResponse.success(hubHistoryResponse);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping("/{hubId}")
  public ResponseEntity<ApiResponse<HubDetailsResponse>> getHubDetails(@PathVariable Long hubId) {
    Hub hub = hubQueryUseCase.getHubDetails(hubWebMapper.toFindQuery(hubId));
    HubDetailsResponse hubDetails = hubWebMapper.toHubDetailsResponse(hub);
    ApiResponse<HubDetailsResponse> response = ApiResponse.success(hubDetails);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
