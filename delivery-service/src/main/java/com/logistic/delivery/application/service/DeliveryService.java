package com.logistic.delivery.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.delivery.application.port.out.DeliveryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class DeliveryService {

  private final DeliveryPersistencePort deliveryPersistencePort;

}
