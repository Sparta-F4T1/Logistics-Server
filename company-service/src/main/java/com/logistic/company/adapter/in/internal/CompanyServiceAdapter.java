package com.logistic.company.adapter.in.internal;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.request.CompanyClientRequest;
import com.logistic.common.internal.response.CompanyClientResponse;
import com.logistic.common.passport.annotation.WithPassport;
import com.logistic.common.passport.model.Passport;
import com.logistic.company.adapter.in.internal.mapper.CompanyServiceMapper;
import com.logistic.company.application.port.in.CompanyQueryUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@Slf4j(topic = "CompanyServiceAdapter")
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/companies")
public class CompanyServiceAdapter {
  private final CompanyQueryUseCase queryUseCase;
  private final CompanyServiceMapper mapper;

  @GetMapping("/{companyId}")
  public CompanyClientResponse findCompany(@PathVariable("companyId") final Long companyId,
                                           @WithPassport Passport passport,
                                           @ModelAttribute final CompanyClientRequest request) {
    return mapper.toCompanyResponse(queryUseCase.findCompany(
        mapper.toFindQuery(companyId, request)));
  }

  @GetMapping
  public List<CompanyClientResponse> findCompanyList(@RequestBody final CompanyClientRequest request) {
    return queryUseCase.findCompanyList(mapper.toListQuery(request))
        .stream().map(mapper::toCompanyResponse).toList();
  }
}
