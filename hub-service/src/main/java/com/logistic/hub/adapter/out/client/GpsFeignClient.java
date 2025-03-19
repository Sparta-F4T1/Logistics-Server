package com.logistic.hub.adapter.out.client;

import com.logistic.common.internal.client.GpsInternalClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "hub-service")
public interface GpsFeignClient extends GpsInternalClient {

}
