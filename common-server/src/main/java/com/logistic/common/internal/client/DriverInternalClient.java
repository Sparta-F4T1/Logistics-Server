package com.logistic.common.internal.client;

import com.logistic.common.internal.request.DriverClientRequest;
import com.logistic.common.internal.response.DriverClientResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface DriverInternalClient {
  @GetMapping("/internal/v1/drivers/{driverId}")
  DriverClientResponse findDriver(@PathVariable("driverId") String driverId, @RequestBody DriverClientRequest request);

  @GetMapping("/internal/v1/drivers")
  List<DriverClientResponse> findDriverList(@RequestBody DriverClientRequest request);
}
