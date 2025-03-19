package com.logistic.order.adaptor.in.web.mapper;

import com.logistic.order.adaptor.in.web.request.CreateOrderRequest;
import com.logistic.order.adaptor.in.web.response.CreateOrderResponse;
import com.logistic.order.adaptor.in.web.response.UpdateOrderResponse;
import com.logistic.order.application.port.in.command.CreateOrderCommand;
import com.logistic.order.domain.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderWebMapper {
  CreateOrderCommand toCreateCommand(CreateOrderRequest createOrderRequest);

  CreateOrderResponse toCreateResponse(Order order);

  UpdateOrderResponse toUpdateResponse(Order order);
}
