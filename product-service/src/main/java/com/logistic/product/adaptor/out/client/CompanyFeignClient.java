package com.logistic.product.adaptor.out.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "company-service")
public interface CompanyFeignClient {
  //todo company-service 통신 구현
  boolean existsById(Long companyId);
}
