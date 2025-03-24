package com.logistic.order.adapter.out.internal.mapper;

import com.logistic.common.internal.message.CreateOrderEvent;
import com.logistic.common.internal.response.CompanyClientResponse;
import com.logistic.common.internal.response.HubClientResponse;
import com.logistic.common.internal.response.UserClientResponse;
import com.logistic.order.application.service.dto.CompanyDto;
import com.logistic.order.application.service.dto.HubDto;
import com.logistic.order.application.service.dto.UserDto;
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
  CreateOrderEvent toCreateOrderEvent(Order order);

  CompanyDto toCompanyDto(CompanyClientResponse companyClientResponse);

  UserDto toUserDto(UserClientResponse userClientResponse);

  HubDto toHubDto(HubClientResponse hubClientResponse);
}
