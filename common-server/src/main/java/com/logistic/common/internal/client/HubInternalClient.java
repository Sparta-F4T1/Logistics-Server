package com.logistic.common.internal.client;

import com.logistic.common.internal.request.HubClientRequest;
import com.logistic.common.internal.response.HubClientResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface HubInternalClient {
  @GetMapping("/internal/v1/hubs/{hubId}")
  HubClientResponse findHub(@PathVariable("hubId") Long hubId, @RequestBody HubClientRequest request);

  @GetMapping("/internal/v1/hubs")
  List<HubClientResponse> findHubList(@RequestBody HubClientRequest request);
}
