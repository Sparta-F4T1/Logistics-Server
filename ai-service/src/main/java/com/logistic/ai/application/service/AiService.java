package com.logistic.ai.application.service;

import com.logistic.ai.application.port.in.AiUseCase;
import com.logistic.ai.application.port.in.command.GetDeadLineCommand;
import com.logistic.ai.application.port.out.ChatGPTPort;
import com.logistic.common.annotation.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class AiService implements AiUseCase {
  private final ChatGPTPort chatGPTPort;

  public String getFinalDispatchDeadline(GetDeadLineCommand command) {
    return chatGPTPort.getResponse(command.orderCreatedAt(),command.totalHubDeliveryTime(),command.arrivalHubAddress(),command.arrivalCompanyAddress());
  }
}
