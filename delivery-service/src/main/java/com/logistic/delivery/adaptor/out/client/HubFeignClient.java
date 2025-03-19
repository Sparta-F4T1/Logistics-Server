package com.logistic.delivery.adaptor.out.client;

import com.logistic.common.internal.client.HubInternalClient;
import org.springframework.stereotype.Component;

//@FeignClient(name = "hub-service")
@Component
public interface HubFeignClient extends HubInternalClient {
}
