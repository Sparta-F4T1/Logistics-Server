package com.logistic.delivery.adaptor.out.persistence;

import com.logistic.common.annotation.PersistenceAdapter;
import com.logistic.delivery.adaptor.out.persistence.mapper.DeliveryPersistenceMapper;
import com.logistic.delivery.application.port.out.DeliveryPersistencePort;
import com.logistic.delivery.domain.Delivery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public class DeliveryPersistenceAdaptor implements DeliveryPersistencePort {
  private final DeliveryPersistenceMapper deliveryPersistenceMapper;
  private final DeliveryEntityRepository deliveryEntityRepository;

  @Override
  public Delivery save(Delivery delivery) {
    DeliveryEntity entity = deliveryEntityRepository.save(deliveryPersistenceMapper.toEntity(delivery));
    log.info("[Delivery] : 저장 완료 (Delivery id = {})", entity.getId());
    return deliveryPersistenceMapper.toDomain(entity);
  }
}
