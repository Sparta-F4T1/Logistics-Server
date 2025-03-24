package com.logistic.driver.adapter.out.internal.client;

import com.logistic.common.internal.client.DeliveryInternalClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "delivery-service")
public interface DeliveryFeignClient extends DeliveryInternalClient {
}
