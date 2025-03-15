package com.logistic.delivery.application.port.out;

import com.logistic.delivery.domain.Delivery;

public interface DeliveryPersistencePort {
  Delivery save(Delivery delivery);
}
