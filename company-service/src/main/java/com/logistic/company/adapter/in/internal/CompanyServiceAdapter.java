package com.logistic.company.adapter.in.internal;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.response.CompanyClientResponse;
import com.logistic.common.passport.annotation.WithPassport;
import com.logistic.common.passport.model.Passport;
import com.logistic.company.adapter.in.internal.mapper.CompanyServiceMapper;
import com.logistic.company.application.port.in.CompanyQueryUseCase;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
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
                                           @WithPassport Passport passport) {
    return mapper.toCompanyResponse(queryUseCase.findCompany(
        mapper.toFindQuery(companyId, passport)));
  }

  @GetMapping
  public List<CompanyClientResponse> findCompanyList(@ModelAttribute final List<Long> companyIds,
                                                     @WithPassport Passport passport) {
    return queryUseCase.findCompanyList(mapper.toListQuery(companyIds, passport))
        .stream().map(mapper::toCompanyResponse).toList();
  }
}
