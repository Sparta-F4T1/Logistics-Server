package com.logistic.delivery.adaptor.out.persistence;

import com.logistic.common.annotation.PersistenceAdapter;
import com.logistic.delivery.application.port.out.DeliveryPersistencePort;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class DeliveryPersistenceAdaptor implements DeliveryPersistencePort {
  private final DeliveryEntityRepository deliveryEntityRepository;


}
