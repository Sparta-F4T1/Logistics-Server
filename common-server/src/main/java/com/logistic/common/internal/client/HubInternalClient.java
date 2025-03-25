package com.logistic.common.internal.client;

import com.logistic.common.internal.response.HubClientResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface HubInternalClient {
  @GetMapping("/internal/v1/hubs/{hubId}")
  HubClientResponse findHub(@PathVariable("hubId") Long hubId);

  @GetMapping("/internal/v1/hubs")
  List<HubClientResponse> findHubList(@RequestParam("hubId") List<Long> hubId);
}
