package com.logistic.delivery.application.port.in;

import com.logistic.delivery.application.port.in.command.DeliveryCreateCommand;
import com.logistic.delivery.domain.Delivery;

public interface DeliveryUseCase {
  Delivery createDelivery(DeliveryCreateCommand command);
}
