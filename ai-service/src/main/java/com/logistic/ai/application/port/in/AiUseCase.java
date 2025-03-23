package com.logistic.ai.application.port.in;

import com.logistic.ai.application.port.in.command.GetDeadLineCommand;

public interface AiUseCase {
  String getFinalDispatchDeadline(GetDeadLineCommand command);
}
