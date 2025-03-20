package com.logistic.product.adapter.out.internal;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.request.ProductClientRequest;
import com.logistic.common.internal.response.CompanyClientResponse;
import com.logistic.common.passport.model.Passport;
import com.logistic.product.adapter.out.internal.client.CompanyFeignClient;
import com.logistic.product.adapter.out.internal.mapper.ProductClientMapper;
import com.logistic.product.application.port.out.ProductInternalPort;
import com.logistic.product.application.service.dto.CompanyInfo;
import com.logistic.product.domain.exception.DomainException.CompanyNotFoundException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class ProductInternalAdapter implements ProductInternalPort {
  private final CompanyFeignClient companyFeignClient;
  private final ProductClientMapper mapper;

  @Override
  public CompanyInfo findCompany(final Long companyId, final Passport passport) {
    ProductClientRequest request = new ProductClientRequest(null, passport);
    try {
      CompanyClientResponse response = companyFeignClient.findCompany(companyId, request);
      return mapper.toCompanyInfo(response);
    } catch (FeignException e) {
      throw new CompanyNotFoundException();
    }
  }
}
