package com.logistic.order.adapter.out.internal.client;

import com.logistic.common.internal.client.ProductInternalClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "product-service", url = "localhost:8081")
public interface ProductFeignClient extends ProductInternalClient {
}
