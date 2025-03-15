package com.logistic.delivery.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.delivery.application.port.in.DeliveryUseCase;
import com.logistic.delivery.application.port.in.command.DeliveryCreateCommand;
import com.logistic.delivery.application.port.out.DeliveryPersistencePort;
import com.logistic.delivery.domain.Delivery;
import com.logistic.delivery.domain.DeliveryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class DeliveryService implements DeliveryUseCase {

  private final DeliveryPersistencePort deliveryPersistencePort;

  @Override
  public Delivery createDelivery(DeliveryCreateCommand command) {
    return deliveryPersistencePort.save(
        Delivery.create(
            command.orderId(),
            DeliveryStatus.HUB_WAITING,
            command.departHubId(),
            command.arrivalHubId(),
            command.driverId()
        )
    );
  }
}
