package com.logistic.order.adapter.out.persistence.mapper;


import com.logistic.order.adapter.out.persistence.OrderEntity;
import com.logistic.order.domain.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderPersistenceMapper {

  @Mapping(target = "sellerId", source = "seller.companyId")
  @Mapping(target = "buyerId", source = "buyer.companyId")
  OrderEntity toEntity(Order order);

  @Mapping(target = "seller.companyId", source = "sellerId")
  @Mapping(target = "buyer.companyId", source = "buyerId")
  Order toDomain(OrderEntity order);
}
