package com.logistic.company.adapter.in.internal.mapper;

import com.logistic.common.internal.request.CompanyClientRequest;
import com.logistic.common.internal.response.CompanyClientResponse;
import com.logistic.company.application.port.in.query.FindCompanyQuery;
import com.logistic.company.application.port.in.query.ListCompanyQuery;
import com.logistic.company.domain.model.CompanyView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyServiceMapper {

  //  @Mapping(source = "request.passport", target = "passport")
  FindCompanyQuery toFindQuery(Long companyId, CompanyClientRequest request);

  //  @Mapping(source = "request.passport", target = "passport")
  ListCompanyQuery toListQuery(CompanyClientRequest request);

  CompanyClientResponse toCompanyResponse(CompanyView companyView);
}
