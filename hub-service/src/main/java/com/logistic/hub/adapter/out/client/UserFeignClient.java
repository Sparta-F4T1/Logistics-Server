package com.logistic.hub.adapter.out.client;

import com.logistic.common.internal.client.UserInternalClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service")
public interface UserFeignClient extends UserInternalClient {
}
