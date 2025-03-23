package com.logistic.ai.adapter.in.internal;

import com.logistic.ai.adapter.in.internal.mapper.AiServiceMapper;
import com.logistic.ai.adapter.in.internal.parser.DateTimeParser;
import com.logistic.ai.adapter.in.internal.request.GetDeadLineRequest;
import com.logistic.ai.adapter.in.internal.response.GetDeadLineResponse;
import com.logistic.ai.application.port.in.AiUseCase;
import com.logistic.common.annotation.Adapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/ai")
public class AiServiceAdapter {
  private final AiUseCase aiUseCase;
  private final AiServiceMapper aiServiceMapper;

  @GetMapping
  public GetDeadLineResponse getDeadLine(@ModelAttribute GetDeadLineRequest request) {
    String deadline = aiUseCase.getFinalDispatchDeadline(aiServiceMapper.toCommand(request));
    log.info("최종 발송 기한 : " + deadline);
    return new GetDeadLineResponse(DateTimeParser.parseToLocalDateTime(deadline));
  }

}
