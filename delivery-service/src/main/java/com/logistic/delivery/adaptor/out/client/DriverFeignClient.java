package com.logistic.delivery.adaptor.out.client;

import com.logistic.common.internal.client.DriverInternalClient;
import org.springframework.stereotype.Component;

//@FeignClient(name = "driver-service")
@Component
public interface DriverFeignClient extends DriverInternalClient {
}
