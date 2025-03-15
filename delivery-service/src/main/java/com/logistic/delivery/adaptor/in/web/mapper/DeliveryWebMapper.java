package com.logistic.delivery.adaptor.in.web.mapper;

import com.logistic.delivery.adaptor.in.web.request.DeliveryCreateRequest;
import com.logistic.delivery.adaptor.in.web.response.DeliveryResponse;
import com.logistic.delivery.application.port.in.command.DeliveryCreateCommand;
import com.logistic.delivery.domain.Delivery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeliveryWebMapper {
  DeliveryCreateCommand toCreateCommand(DeliveryCreateRequest request);

  @Mapping(source = "id", target = "deliveryId")
  DeliveryResponse toResponse(Delivery delivery);

}
