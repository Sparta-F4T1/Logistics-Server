package com.logistic.delivery.adapter.out.client;

import com.logistic.common.internal.client.RouteInternalClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "hub-service")
public interface RouteFeignClient extends RouteInternalClient {
}
