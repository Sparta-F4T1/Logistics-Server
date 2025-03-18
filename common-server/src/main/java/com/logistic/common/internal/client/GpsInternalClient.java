package com.logistic.common.internal.client;

import com.logistic.common.internal.request.GpsClientRequest;
import com.logistic.common.internal.response.GpsClientResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface GpsInternalClient {
  @GetMapping("/internal/v1/gps/{gpsId}")
  GpsClientResponse findGps(@PathVariable("gpsId") Long gpsId, @RequestBody GpsClientRequest request);

  @GetMapping("/internal/v1/gps")
  List<GpsClientResponse> findGpsList(@RequestBody GpsClientRequest request);
}
