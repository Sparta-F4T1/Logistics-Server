package com.logistic.common.internal.client;

import com.logistic.common.internal.response.GpsClientResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface GpsInternalClient {
  @GetMapping("/internal/v1/gps")
  GpsClientResponse findGps(@RequestParam String road);

  @GetMapping("/internal/v1/gps/direction")
  GpsClientResponse findDistanceAndDuration(@RequestParam String depart, @RequestParam String arrival);
}
