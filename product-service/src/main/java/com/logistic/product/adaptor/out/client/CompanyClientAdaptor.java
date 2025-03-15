package com.logistic.product.adaptor.out.client;

import com.logistic.product.application.port.out.CompanyClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyClientAdaptor implements CompanyClientPort {
  private final CompanyFeignClient companyFeignClient;

  @Override
  public boolean existsById(final Long companyId) {
    companyFeignClient.existsById(companyId);
    //todo ResponseEntity -> boolean 으로 변환 로직 구현
    return true;
  }
}
