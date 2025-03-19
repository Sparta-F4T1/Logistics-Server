package com.logistic.delivery.adaptor.out.persistence.repository;

import com.logistic.common.annotation.Adapter;
import com.logistic.delivery.adaptor.out.persistence.DeliveryEntity;
import com.logistic.delivery.adaptor.out.persistence.mapper.DeliveryPersistenceMapper;
import com.logistic.delivery.application.port.out.DeliveryPersistencePort;
import com.logistic.delivery.domain.Delivery;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
@RequiredArgsConstructor
public class DeliveryPersistenceAdapter implements DeliveryPersistencePort {
  private final DeliveryPersistenceMapper deliveryPersistenceMapper;
  private final DeliveryJpaRepository deliveryJpaRepository;

  @Override
  public Delivery save(Delivery delivery) {
    DeliveryEntity entity = deliveryJpaRepository.save(deliveryPersistenceMapper.toEntity(delivery));
    log.info("[Delivery] : 저장 완료 (Delivery id = {})", entity.getId());
    return deliveryPersistenceMapper.toDomain(entity);
  }

  @Override
  public Optional<Delivery> findById(Long id) {
    // todo: 예외 처리
    Optional<DeliveryEntity> entity = deliveryJpaRepository.findById(id);
    return deliveryPersistenceMapper.toDomain(entity);
  }

}
