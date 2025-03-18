package com.logistic.common.internal.client;

import com.logistic.common.internal.request.OrderClientRequest;
import com.logistic.common.internal.response.OrderClientResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface OrderInternalClient {
  @GetMapping("/internal/v1/orders/{orderId}")
  OrderClientResponse findOrder(@PathVariable("orderId") Long orderId, @RequestBody OrderClientRequest request);

  @GetMapping("/internal/v1/orders")
  List<OrderClientResponse> findOrderList(@RequestBody OrderClientRequest request);
}
