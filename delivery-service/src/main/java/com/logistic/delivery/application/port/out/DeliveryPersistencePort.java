package com.logistic.delivery.application.port.out;

import com.logistic.delivery.domain.Delivery;
import java.util.Optional;

public interface DeliveryPersistencePort {
  Delivery save(Delivery delivery);
  Optional<Delivery> findById(Long id);
}
