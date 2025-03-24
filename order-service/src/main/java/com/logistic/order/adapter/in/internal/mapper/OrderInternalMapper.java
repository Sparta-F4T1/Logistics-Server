package com.logistic.order.adapter.in.internal.mapper;

import com.logistic.common.internal.response.OrderClientResponse;
import com.logistic.order.domain.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderInternalMapper {
  @Mapping(source = "id", target = "orderId")
  OrderClientResponse toClientResponse(Order order);
}
