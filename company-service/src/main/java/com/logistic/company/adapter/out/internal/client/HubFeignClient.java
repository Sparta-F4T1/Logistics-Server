package com.logistic.company.adapter.out.internal.client;

import com.logistic.common.internal.client.HubInternalClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "hub-service")
public interface HubFeignClient extends HubInternalClient {
}
