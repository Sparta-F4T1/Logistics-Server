package com.logistic.order.adapter.in.web;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.order.adapter.in.web.mapper.OrderWebMapper;
import com.logistic.order.adapter.in.web.request.CreateOrderRequest;
import com.logistic.order.adapter.in.web.response.CreateOrderResponse;
import com.logistic.order.adapter.in.web.response.ReadOrderResponse;
import com.logistic.order.adapter.in.web.response.UpdateOrderResponse;
import com.logistic.order.application.port.in.OrderUseCase;
import com.logistic.order.domain.Order;
import com.logistic.order.domain.OrderStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public ResponseEntity<ApiResponse<CreateOrderResponse>> createOrder(
      @Valid @RequestBody CreateOrderRequest createOrderRequest) {
    Order order = orderUseCase.createOrder(orderWebMapper.toCreateCommand(createOrderRequest));
    ApiResponse<CreateOrderResponse> response = ApiResponse.success(orderWebMapper.toCreateResponse(order));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PatchMapping("/{orderId}/{status}")
  public ResponseEntity<ApiResponse<UpdateOrderResponse>> updateOrderStatus(@PathVariable Long orderId,
                                                                            @PathVariable OrderStatus status) {
    Order order = orderUseCase.updateOrder(orderId, status);
    ApiResponse<UpdateOrderResponse> response = ApiResponse.success(orderWebMapper.toUpdateResponse(order));
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Long orderId) {
    orderUseCase.deleteOrder(orderId, "userId");
    ApiResponse<Void> response = ApiResponse.success();
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<ApiResponse<ReadOrderResponse>> findOrder(@PathVariable Long orderId) {
    Order order = orderUseCase.findOrder(orderId);
    ApiResponse<ReadOrderResponse> response = ApiResponse.success(orderWebMapper.toReadOrderResponse(order));
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
  }
}
