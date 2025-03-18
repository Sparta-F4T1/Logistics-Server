package com.logistic.order.adaptor.in.web;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.order.adaptor.in.web.mapper.OrderWebMapper;
import com.logistic.order.adaptor.in.web.request.OrderCreateRequest;
import com.logistic.order.adaptor.in.web.response.OrderCreateResponse;
import com.logistic.order.application.port.in.OrderUseCase;
import com.logistic.order.domain.Order;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderWebAdapter {
  private final OrderUseCase orderUseCase;
  private final OrderWebMapper orderWebMapper;

  @PostMapping
  public ResponseEntity<ApiResponse<OrderCreateResponse>> createOrder(
      @Valid @RequestBody OrderCreateRequest orderCreateRequest) {
    Order order = orderUseCase.createOrder(orderWebMapper.toCreateCommand(orderCreateRequest));
    ApiResponse<OrderCreateResponse> response = ApiResponse.success(orderWebMapper.toCreateResponse(order));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

}
