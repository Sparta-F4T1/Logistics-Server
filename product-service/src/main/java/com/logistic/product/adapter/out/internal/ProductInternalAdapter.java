package com.logistic.product.adapter.out.internal;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.response.CompanyClientResponse;
import com.logistic.common.internal.response.HubClientResponse;
import com.logistic.product.adapter.out.internal.client.CompanyFeignClient;
import com.logistic.product.adapter.out.internal.client.HubFeignClient;
import com.logistic.product.adapter.out.internal.mapper.ProductClientMapper;
import com.logistic.product.application.port.out.ProductInternalPort;
import com.logistic.product.domain.exception.CustomNotFoundException.CompanyNotFoundException;
import com.logistic.product.domain.exception.CustomNotFoundException.HubNotFoundException;
import com.logistic.product.domain.vo.Company;
import com.logistic.product.domain.vo.Hub;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class ProductInternalAdapter implements ProductInternalPort {
  private final ProductClientMapper mapper;
  private final HubFeignClient hubFeignClient;
  private final CompanyFeignClient companyFeignClient;

  @Override
  public Company findCompany(final Long companyId) {
    try {
      CompanyClientResponse response = companyFeignClient.findCompany(companyId);
      return mapper.toCompany(response);
    } catch (FeignException e) {
      throw new CompanyNotFoundException();
    }
  }

  @Override
  public Hub findHub(final Long hubId) {
    try {
      HubClientResponse response = hubFeignClient.findHub(hubId);
      return mapper.toHub(response);
    } catch (FeignException e) {
      throw new HubNotFoundException();
    }
  }
}
