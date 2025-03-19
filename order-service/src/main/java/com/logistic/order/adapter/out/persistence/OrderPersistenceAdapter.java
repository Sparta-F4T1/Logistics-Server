package com.logistic.order.adapter.out.persistence;

import com.logistic.common.annotation.Adapter;
import com.logistic.order.adapter.out.persistence.mapper.OrderPersistenceMapper;
import com.logistic.order.adapter.out.persistence.repository.OrderJpaRepository;
import com.logistic.order.adapter.out.persistence.repository.OrderQueryDslRepository;
import com.logistic.order.application.port.OrderPersistencePort;
import com.logistic.order.domain.Order;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderPersistencePort {
  private final OrderJpaRepository orderJpaRepository;
  private final OrderPersistenceMapper orderPersistenceMapper;

  @Override
  public Order save(Order order) {
    OrderEntity saveObj = orderPersistenceMapper.toEntity(order);
    OrderEntity entity = orderJpaRepository.save(saveObj);
    return orderPersistenceMapper.toDomain(entity);
  }

  @Override
  public void delete(Long orderId, String userId) {
    Optional<OrderEntity> orderEntity = orderJpaRepository.findById(orderId);

    orderEntity.ifPresent(entity -> entity.delete(true, userId));
  }

  @Override
  public Optional<Order> findById(Long orderId) {
    Optional<OrderEntity> orderEntity = orderJpaRepository.findById(orderId);

    return orderEntity.map(orderPersistenceMapper::toDomain);
  }
}
