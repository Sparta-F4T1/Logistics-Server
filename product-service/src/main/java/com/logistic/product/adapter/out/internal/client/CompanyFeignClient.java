package com.logistic.product.adapter.out.internal.client;

import com.logistic.common.internal.client.CompanyInternalClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "company-service")
public interface CompanyFeignClient extends CompanyInternalClient {

}
