package com.logistic.order.adapter.out.internal.mapper;

import com.logistic.order.application.port.out.command.CreateDeliveryCommand;
import com.logistic.order.domain.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderClientMapper {

  @Mapping(source = "sellerId", target = "departCompanyId")
  @Mapping(source = "buyerId", target = "arrivalCompanyId")
  @Mapping(source = "sellerHubId", target = "departHubId")
  @Mapping(source = "buyerHubId", target = "arrivalHubId")
  CreateDeliveryCommand toDeliveryCommand(Order order);
}
