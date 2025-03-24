package com.logistic.order.adapter.out.internal;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.request.ProductClientRequest;
import com.logistic.order.adapter.out.internal.client.CompanyFeignClient;
import com.logistic.order.adapter.out.internal.client.ProductFeignClient;
import com.logistic.order.adapter.out.internal.client.UserFeignClient;
import com.logistic.order.adapter.out.internal.mapper.OrderClientMapper;
import com.logistic.order.application.port.out.OrderInternalPort;
import com.logistic.order.application.service.dto.CompanyDto;
import com.logistic.order.application.service.dto.UserDto;
import com.logistic.order.domain.vo.OrderProduct;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class OrderInternalAdapter implements OrderInternalPort {
  private final ProductFeignClient productFeignClient;
  private final UserFeignClient userFeignClient;
  private final CompanyFeignClient companyFeignClient;
  private final OrderClientMapper orderClientMapper;

  public void updateStock(List<OrderProduct> orderProducts){
    ProductClientRequest request = new ProductClientRequest(orderProducts.stream()
        .collect(Collectors.toMap(OrderProduct::getProductId, OrderProduct::getQuantity)));
    productFeignClient.updateStock(request);
  }

  @Override
  public CompanyDto findCompany(Long companyId) {
    return orderClientMapper.toCompanyDto(companyFeignClient.findCompany(companyId));
  }

  @Override
  public UserDto findUser(String userId) {
    return orderClientMapper.toUserDto(userFeignClient.findUser(userId));
  }
}
