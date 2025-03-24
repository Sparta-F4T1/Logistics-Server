package com.logistic.order.adapter.in.internal;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.response.OrderClientResponse;
import com.logistic.common.passport.model.RoleType;
import com.logistic.common.passport.model.UserInfo;
import com.logistic.order.adapter.in.internal.mapper.OrderInternalMapper;
import com.logistic.order.application.port.in.OrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequestMapping("/internal/v1/orders")
@RequiredArgsConstructor
public class OrderServiceAdapter {

  private final OrderUseCase orderUseCase;
  private final OrderInternalMapper orderInternalMapper;

  @GetMapping("/{orderId}")
  public OrderClientResponse findOrder(@PathVariable("orderId") Long orderId){
    UserInfo userInfo = new UserInfo("SYSTEM", String.valueOf(RoleType.MASTER_ADMIN));
    return orderInternalMapper.toClientResponse(orderUseCase.findOrder(orderId, userInfo));
  }

}
