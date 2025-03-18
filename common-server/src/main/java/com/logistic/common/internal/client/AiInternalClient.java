package com.logistic.common.internal.client;

import com.logistic.common.internal.request.AiClientRequest;
import com.logistic.common.internal.response.AiClientResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface AiInternalClient {
  @GetMapping("/internal/v1/ai/{aiId}")
  AiClientResponse findAi(@PathVariable("aiId") Long aiId, @RequestBody AiClientRequest request);

  @GetMapping("/internal/v1/ai")
  List<AiClientResponse> findAiList(@RequestBody AiClientRequest request);
}
