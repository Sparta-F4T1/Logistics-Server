package com.logistic.delivery.adapter.in.message.mapper;

import com.logistic.common.internal.message.CreateOrderEvent;
import com.logistic.delivery.application.port.in.command.DeliveryCreateCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryEventMapper {
  DeliveryCreateCommand toCreateCommand(CreateOrderEvent event);
}
