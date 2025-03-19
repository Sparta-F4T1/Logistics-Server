package com.logistic.order.adaptor.in.web.mapper;

import com.logistic.order.adaptor.in.web.request.OrderCreateRequest;
import com.logistic.order.adaptor.in.web.response.OrderCreateResponse;
import com.logistic.order.application.port.in.command.OrderCreateCommand;
import com.logistic.order.domain.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderWebMapper {
  CreateOrderCommand toCreateCommand(CreateOrderRequest createOrderRequest);

  CreateOrderResponse toCreateResponse(Order order);

  UpdateOrderResponse toUpdateResponse(Order order);
}
