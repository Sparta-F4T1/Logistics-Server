package com.logistic.delivery.adapter.in.web.mapper;

import com.logistic.delivery.adapter.in.web.request.DeliveryCreateRequest;
import com.logistic.delivery.adapter.in.web.request.DeliverySearchRequest;
import com.logistic.delivery.adapter.in.web.request.DeliveryUpdateRequest;
import com.logistic.delivery.adapter.in.web.request.HubDeliveryHistoryUpdateRequest;
import com.logistic.delivery.adapter.in.web.response.DeliveryResponse;
import com.logistic.delivery.adapter.in.web.response.HubDeliveryHistoryResponse;
import com.logistic.delivery.application.port.in.command.DeliveryCreateCommand;
import com.logistic.delivery.application.port.in.command.DeliveryDeleteCommand;
import com.logistic.delivery.application.port.in.command.DeliveryUpdateCommand;
import com.logistic.delivery.application.port.in.command.HubDeliveryHistoryUpdateCommand;
import com.logistic.delivery.application.port.in.query.DeliveryFindQuery;
import com.logistic.delivery.application.port.in.query.DeliverySearchQuery;
import com.logistic.delivery.domain.Delivery;
import com.logistic.delivery.domain.HubDeliveryHistory;
import com.logistic.delivery.domain.view.DeliveryView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeliveryWebMapper {
  DeliveryCreateCommand toCreateCommand(DeliveryCreateRequest request);

  @Mapping(source = "id", target = "deliveryId")
  DeliveryResponse toResponse(Delivery delivery);

  DeliveryUpdateCommand toUpdateCommand(DeliveryUpdateRequest request);

  HubDeliveryHistoryUpdateCommand toUpdateCommand(HubDeliveryHistoryUpdateRequest request);

  HubDeliveryHistoryResponse toResponse(HubDeliveryHistory hubDeliveryHistory);

  DeliveryFindQuery toQuery(Long deliveryId);

  @Mapping(source = "id", target = "deliveryId")
  DeliveryResponse toResponse(DeliveryView deliveryView);

  DeliverySearchQuery toQuery(DeliverySearchRequest request);

  DeliveryDeleteCommand toDeleteCommand(Long deliveryId);

}
