package com.logistic.hub.adapter.in.web;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.hub.adapter.in.web.mapper.HubWebMapper;
import com.logistic.hub.adapter.in.web.request.HubCreateRequest;
import com.logistic.hub.adapter.in.web.request.HubUpdateRequest;
import com.logistic.hub.adapter.in.web.response.HubCreateResponse;
import com.logistic.hub.application.port.in.HubUseCase;
import com.logistic.hub.application.port.in.command.HubCreateCommand;
import com.logistic.hub.application.port.in.command.HubUpdateCommand;
import com.logistic.hub.domain.Hub;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hubs")
public class HubWebAdaptor {
  private final HubUseCase hubUseCase;
  private final HubWebMapper hubWebMapper;

  @PostMapping
  public ResponseEntity<ApiResponse<HubCreateResponse>> createHub(//@WithPassport Passport passport,
                                                                  @Valid @RequestBody HubCreateRequest request) {

    HubCreateCommand command = hubWebMapper.toHubCreateCommand(request);
    Hub hub = hubUseCase.createHub(command);
    HubCreateResponse hubResponse = hubWebMapper.toHubCreateResponse(hub);
    ApiResponse<HubCreateResponse> response = ApiResponse.success(hubResponse);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{hubId}")
  public ResponseEntity<ApiResponse<String>> updateHub(@PathVariable Long hubId,
                                                       //@WithPassport Passport passport,
                                                       @Valid @RequestBody HubUpdateRequest request) {
    HubUpdateCommand command = hubWebMapper.toHubUpdateCommand(request);
    hubUseCase.updateHub(hubId, command);

    ApiResponse<String> response = ApiResponse.success("수정되었습니다");
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @DeleteMapping("/{hubId}")
  public ResponseEntity<ApiResponse<String>> deleteHub(//@WithPassport Passport passport,
                                                       @PathVariable Long hubId) {

    hubUseCase.deleteHub(hubId);

    ApiResponse<String> response = ApiResponse.success("삭제되었습니다");
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
