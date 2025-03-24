package com.logistic.driver.adapter.in.internal;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.request.DriverClientRequest;
import com.logistic.common.internal.response.DriverClientResponse;
import com.logistic.common.passport.annotation.WithPassport;
import com.logistic.common.passport.model.Passport;
import com.logistic.driver.adapter.in.internal.mapper.DriverServiceMapper;
import com.logistic.driver.application.port.in.DriverCommandUseCase;
import com.logistic.driver.application.port.in.DriverQueryUseCase;
import com.logistic.driver.domain.model.Driver;
import com.logistic.driver.domain.model.DriverView;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/drivers")
public class DriverServiceAdapter {
  private final DriverServiceMapper mapper;
  private final DriverQueryUseCase queryUseCase;
  private final DriverCommandUseCase commandUseCase;

  @GetMapping("/{driverId}")
  public DriverClientResponse findDriver(@PathVariable final String driverId,
                                         @WithPassport final Passport passport) {
    final DriverView driverView = queryUseCase.findDriver(mapper.toFindDriverQuery(driverId, passport));
    return mapper.toResponse(driverView);
  }

  @GetMapping
  public List<DriverClientResponse> findDriverList(@ModelAttribute final DriverClientRequest request,
                                                   @WithPassport final Passport passport) {
    final List<DriverView> driverViewList = queryUseCase.findDriverList(
        mapper.toListDriverQuery(request, passport));
    return mapper.toResponseList(driverViewList);
  }

  @GetMapping("/hub")
  public List<DriverClientResponse> getHubDriver(@ModelAttribute final DriverClientRequest request,
                                                 @WithPassport final Passport passport) {
    List<Driver> hubDriverList = commandUseCase.getHubDriverList(mapper.toGetHubDriverCommand(request, passport));
    return hubDriverList.stream().map(mapper::toResponse).toList();
  }

  @PostMapping("/company")
  public void assignCompanyDrivers(@RequestBody final DriverClientRequest request,
                                   @WithPassport Passport passport) {
    commandUseCase.assignCompanyDrivers(mapper.toAssignCompanyDriversCommand(request, passport));
  }
}
