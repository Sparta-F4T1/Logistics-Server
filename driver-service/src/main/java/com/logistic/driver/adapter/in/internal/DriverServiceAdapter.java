package com.logistic.driver.adapter.in.internal;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.request.DriverClientRequest;
import com.logistic.common.internal.response.DriverClientResponse;
import com.logistic.driver.application.port.in.DriverQueryUseCase;
import com.logistic.driver.domain.Driver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/drivers")
public class DriverServiceAdapter {
  private final DriverQueryUseCase driverQueryUseCase;
  private final DriverServiceMapper mapper;

  @GetMapping("/{driverId}")
  public DriverClientResponse findDriver(@PathVariable final String driverId,
                                         @RequestBody final DriverClientRequest request) {
    final Driver driver = driverQueryUseCase.findDriver(mapper.toFindDriverQuery(driverId, request));
    return mapper.toResponse(driver);
  }

  @GetMapping
  public List<DriverClientResponse> findDriverList(@RequestBody final DriverClientRequest request) {
    final List<Driver> driverList = driverQueryUseCase.findDriverList(mapper.toListDriverQuery(request));
    return mapper.toResponseList(driverList);
  }
}
