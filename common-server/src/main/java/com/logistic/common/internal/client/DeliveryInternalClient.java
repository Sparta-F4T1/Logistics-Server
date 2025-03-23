package com.logistic.common.internal.client;

import com.logistic.common.internal.response.DeliveryClientResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface DeliveryInternalClient {
  @GetMapping("/internal/v1/deliveries/{deliveryId}")
  DeliveryClientResponse findDelivery(@PathVariable("deliveryId") Long deliveryId);

  @GetMapping("/internal/v1/deliveries")
  List<DeliveryClientResponse> findDeliveryList(@RequestParam List<Long> deliveryIds);
}
