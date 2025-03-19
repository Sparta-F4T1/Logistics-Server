package com.logistic.driver.adapter.in.external.web;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.passport.model.Passport;
import com.logistic.common.response.ApiResponse;
import com.logistic.driver.adapter.in.external.web.mapper.DriverWebMapper;
import com.logistic.driver.adapter.in.external.web.request.CreateDriverRequest;
import com.logistic.driver.adapter.in.external.web.request.SearchDriverRequest;
import com.logistic.driver.adapter.in.external.web.request.UpdateDriverRequest;
import com.logistic.driver.adapter.in.external.web.response.FindDriverResponse;
import com.logistic.driver.application.port.in.DriverQueryUseCase;
import com.logistic.driver.application.port.in.DriverUseCase;
import com.logistic.driver.domain.Driver;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverWebAdapter {
  private final DriverUseCase driverUseCase;
  private final DriverQueryUseCase driverQueryUseCase;
  private final DriverWebMapper driverWebMapper;

  @PostMapping
  public ResponseEntity<ApiResponse<FindDriverResponse>> createDriver(
      @Valid @RequestBody final CreateDriverRequest request,
      final Passport passport) {
    final Driver driver = driverUseCase.createDriver(driverWebMapper.toCreateCommand(request, passport));
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(driverWebMapper.toResponse(driver)));
  }

  @PutMapping("/{driverId}")
  public ResponseEntity<ApiResponse<FindDriverResponse>> updateDriver(
      @PathVariable final String driverId,
      @Valid @RequestBody final UpdateDriverRequest request,
      final Passport passport) {
    final Driver driver = driverUseCase.updateDriver(driverWebMapper.toUpdateCommand(driverId, request, passport));
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(driverWebMapper.toResponse(driver)));
  }

  @DeleteMapping("/{driverId}")
  public ResponseEntity<ApiResponse<Void>> deleteDriver(
      @PathVariable final String driverId,
      final Passport passport) {
    driverUseCase.deleteDriver(driverWebMapper.toDeleteCommand(driverId, passport));
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success());
  }

  @GetMapping("/{driverId}")
  public ResponseEntity<ApiResponse<FindDriverResponse>> findDriver(
      @PathVariable final String driverId,
      final Passport passport) {
    final Driver driver = driverQueryUseCase.findDriver(driverWebMapper.toFindQuery(driverId, passport));
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(driverWebMapper.toResponse(driver)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<Page<FindDriverResponse>>> search(
      @ModelAttribute final SearchDriverRequest request,
      @PageableDefault final Pageable pageable,
      final Passport passport) {
    final Page<FindDriverResponse> response = driverQueryUseCase.search(
            driverWebMapper.toSearchQuery(request, pageable, passport))
        .map(driverWebMapper::toResponse);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response));
  }
}
