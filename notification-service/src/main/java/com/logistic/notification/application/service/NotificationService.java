package com.logistic.notification.application.service;

import com.logistic.notification.application.port.in.NotificationUseCase;
import com.logistic.notification.application.port.in.command.SlackMessageCreateCommand;
import com.logistic.notification.application.port.out.SlackMessagePersistencePort;
import com.logistic.notification.application.service.util.MessageTemplateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService implements NotificationUseCase {

  private final SlackMessagePersistencePort slackMessagePersistencePort;

  @Override
  public void createSlackMessage(SlackMessageCreateCommand command) {
    String slackMessage = MessageTemplateUtil.createSlackMessage(command);
    slackMessagePersistencePort.create(command.driverId(),slackMessage);
  }
}
