package com.logistic.notification.application.port.in;

import com.logistic.notification.application.port.in.command.SlackMessageCreateCommand;

public interface NotificationUseCase {
  void createSlackMessage(SlackMessageCreateCommand command);
}
