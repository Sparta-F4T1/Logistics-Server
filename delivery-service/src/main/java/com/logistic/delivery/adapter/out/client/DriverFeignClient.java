package com.logistic.delivery.adapter.out.client;

import com.logistic.common.internal.client.DriverInternalClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "driver-service")
public interface DriverFeignClient extends DriverInternalClient {
}
