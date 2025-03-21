package com.logistic.order.adapter.out.internal.client;

import com.logistic.common.internal.client.ProductInternalClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "product-service")
public interface ProductFeignClient extends ProductInternalClient {
}
