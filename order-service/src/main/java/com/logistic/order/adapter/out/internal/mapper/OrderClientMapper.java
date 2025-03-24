package com.logistic.order.adapter.out.internal.mapper;

import com.logistic.common.internal.message.CreateOrderEvent;
import com.logistic.order.domain.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderClientMapper {

  @Mapping(source = "id", target = "orderId")
  @Mapping(source = "sellerId", target = "departCompanyId")
  @Mapping(source = "buyerId", target = "arrivalCompanyId")
  @Mapping(source = "sellerHubId", target = "departHubId")
  @Mapping(source = "buyerHubId", target = "arrivalHubId")
  CreateOrderEvent toDeliveryCommand(Order order);
}
