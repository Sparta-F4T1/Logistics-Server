package com.logistic.order.adapter.out.internal.mapper;

import com.logistic.common.internal.message.CreateOrderEvent;
import com.logistic.common.internal.response.CompanyClientResponse;
import com.logistic.common.internal.response.HubClientResponse;
import com.logistic.common.internal.response.ProductClientResponse;
import com.logistic.common.internal.response.UserClientResponse;
import com.logistic.order.application.service.dto.CompanyDto;
import com.logistic.order.application.service.dto.HubDto;
import com.logistic.order.application.service.dto.UserDto;
import com.logistic.order.domain.Order;
import com.logistic.order.domain.vo.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderClientMapper {

  @Mapping(source = "order.id", target = "orderId")
  @Mapping(source = "order.seller.companyId", target = "departCompanyId")
  @Mapping(source = "order.buyer.companyId", target = "arrivalCompanyId")
  @Mapping(source = "order.seller.hubId", target = "departHubId")
  @Mapping(source = "order.buyer.hubId", target = "arrivalHubId")
  CreateOrderEvent toCreateOrderEvent(Order order, String slackEmail);

  CompanyDto toCompanyDto(CompanyClientResponse companyClientResponse);

  UserDto toUserDto(UserClientResponse userClientResponse);

  HubDto toHubDto(HubClientResponse hubClientResponse);

  Product toProduct(ProductClientResponse product);
}
