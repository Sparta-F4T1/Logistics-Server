package com.logistic.notification.adapter.in.message.mapper;

import com.logistic.notification.adapter.in.message.CreateDeliveryEvent;
import com.logistic.notification.application.port.in.command.SlackMessageCreateCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationClientMapper {
  SlackMessageCreateCommand toCommand(CreateDeliveryEvent event);
}