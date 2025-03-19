package com.logistic.order.adapter.out.persistence.mapper;


import com.logistic.order.adapter.out.persistence.OrderEntity;
import com.logistic.order.domain.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderPersistenceMapper {
  OrderEntity toEntity(Order order);

  Order toDomain(OrderEntity order);
}
