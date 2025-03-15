package com.logistic.delivery.adaptor.out.persistence.mapper;

import com.logistic.delivery.adaptor.in.web.request.DeliveryCreateRequest;
import com.logistic.delivery.adaptor.out.persistence.DeliveryEntity;
import com.logistic.delivery.application.port.in.command.DeliveryCreateCommand;
import com.logistic.delivery.domain.Delivery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryPersistenceMapper {
  DeliveryEntity toEntity(Delivery domain);
  Delivery toDomain(DeliveryEntity entity);
}