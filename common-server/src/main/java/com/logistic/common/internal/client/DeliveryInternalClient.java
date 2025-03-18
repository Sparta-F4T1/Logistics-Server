package com.logistic.common.internal.client;

import com.logistic.common.internal.request.DeliveryClientRequest;
import com.logistic.common.internal.response.DeliveryClientResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface DeliveryInternalClient {
  @GetMapping("/internal/v1/deliveries/{deliveryId}")
  DeliveryClientResponse findDelivery(@PathVariable("deliveryId") Long deliveryId,
                                      @RequestBody DeliveryClientRequest request);

  @GetMapping("/internal/v1/deliveries")
  List<DeliveryClientResponse> findDeliveryList(@RequestBody DeliveryClientRequest request);
}
