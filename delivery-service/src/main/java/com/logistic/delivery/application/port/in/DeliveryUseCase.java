package com.logistic.delivery.application.port.in;

import com.logistic.delivery.application.port.in.command.DeliveryCreateCommand;
import com.logistic.delivery.application.port.in.command.DeliveryDeleteCommand;
import com.logistic.delivery.application.port.in.command.DeliveryUpdateCommand;
import com.logistic.delivery.application.port.in.command.HubDeliveryHistoryUpdateCommand;
import com.logistic.delivery.domain.Delivery;
import com.logistic.delivery.domain.HubDeliveryHistory;

public interface DeliveryUseCase {
  Delivery createDelivery(DeliveryCreateCommand command);
  Delivery updateDelivery(Long deliveryId, DeliveryUpdateCommand command);
  HubDeliveryHistory updateHubDeliveryHistory(Long deliveryId, HubDeliveryHistoryUpdateCommand command);
  void deleteDelivery(DeliveryDeleteCommand command);
}
