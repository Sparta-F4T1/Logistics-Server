package com.logistic.order.adapter.in.web.mapper;

import com.logistic.order.adapter.in.web.request.CreateOrderRequest;
import com.logistic.order.adapter.in.web.request.SearchOrderRequest;
import com.logistic.order.adapter.in.web.response.CreateOrderResponse;
import com.logistic.order.adapter.in.web.response.ReadOrderResponse;
import com.logistic.order.adapter.in.web.response.SearchOrderResponse;
import com.logistic.order.adapter.in.web.response.UpdateOrderResponse;
import com.logistic.order.application.port.in.command.CreateOrderCommand;
import com.logistic.order.application.port.in.query.SearchOrderQuery;
import com.logistic.order.domain.Order;
import org.mapstruct.Mapper;
import org.springframework.data.web.PagedModel;

@Mapper(componentModel = "spring")
public interface OrderWebMapper {
  CreateOrderCommand toCreateCommand(CreateOrderRequest createOrderRequest);

  CreateOrderResponse toCreateResponse(Order order);

  UpdateOrderResponse toUpdateResponse(Order order);

  ReadOrderResponse toReadOrderResponse(Order order);

  SearchOrderQuery toSearchQuery(SearchOrderRequest searchOrderRequest);

  PagedModel<SearchOrderResponse> toSearchOrderResponse(PagedModel<Order> orders);
}
