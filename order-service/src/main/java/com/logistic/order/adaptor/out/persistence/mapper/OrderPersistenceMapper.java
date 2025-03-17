package com.logistic.order.adaptor.out.persistence.mapper;


import com.logistic.order.adaptor.out.persistence.OrderEntity;
import com.logistic.order.adaptor.out.persistence.OrderProductEntity;
import com.logistic.order.domain.Order;
import com.logistic.order.domain.OrderProduct;
import java.util.List;
import java.util.Optional;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OrderPersistenceMapper {
  @Mapping(target = "orderProducts", qualifiedByName = "toProductEntities")
  OrderEntity toEntity(Order order);

  @Named("toProductEntities")
  List<OrderProductEntity> toProductEntities(List<OrderProduct> orderProduct);

  Order toDomain(OrderEntity order);

  @AfterMapping
  default void setProducts(@MappingTarget OrderEntity order){
    order.getOrderProducts()
        .forEach(product -> product.setOrder(order));
  }
}
