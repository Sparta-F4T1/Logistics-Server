package com.logistic.common.internal.client;

import com.logistic.common.internal.request.CompanyClientRequest;
import com.logistic.common.internal.request.ProductClientRequest;
import com.logistic.common.internal.response.CompanyClientResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface CompanyInternalClient {
  @GetMapping("/internal/v1/companies/{companyId}")
  CompanyClientResponse findCompany(@PathVariable("companyId") Long companyId,
                                    @RequestBody ProductClientRequest request);

  @GetMapping("/internal/v1/companies")
  List<CompanyClientResponse> findCompanyList(@RequestBody CompanyClientRequest request);
}
