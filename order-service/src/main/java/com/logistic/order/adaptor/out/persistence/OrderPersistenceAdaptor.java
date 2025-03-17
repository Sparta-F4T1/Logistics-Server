package com.logistic.order.adaptor.out.persistence;

import com.logistic.common.annotation.PersistenceAdapter;
import com.logistic.order.adaptor.out.persistence.mapper.OrderPersistenceMapper;
import com.logistic.order.adaptor.out.persistence.repository.OrderJpaRepository;
import com.logistic.order.application.port.OrderPersistencePort;
import com.logistic.order.domain.Order;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class OrderPersistenceAdaptor implements OrderPersistencePort {
  private final OrderJpaRepository orderJpaRepository;
  private final OrderPersistenceMapper orderPersistenceMapper;

  @Override
  public Order save(Order order) {
    OrderEntity saveObj = orderPersistenceMapper.toEntity(order);
    OrderEntity entity = orderJpaRepository.save(saveObj);
    return orderPersistenceMapper.toDomain(entity);
  }
}
