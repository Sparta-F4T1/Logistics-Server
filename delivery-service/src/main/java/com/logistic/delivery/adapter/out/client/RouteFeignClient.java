package com.logistic.delivery.adapter.out.client;

import com.logistic.common.internal.client.RouteInternalClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(name = "hub-service")
public interface RouteFeignClient extends RouteInternalClient {
}
