package com.logistic.common.internal.client;

import com.logistic.common.internal.request.GetDeadLineRequest;
import com.logistic.common.internal.response.AiClientResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AiInternalClient {
  @PostMapping("/internal/v1/ai")
  AiClientResponse getDeadLine(@RequestBody GetDeadLineRequest request);
}
