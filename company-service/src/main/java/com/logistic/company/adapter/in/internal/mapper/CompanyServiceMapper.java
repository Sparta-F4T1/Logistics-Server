package com.logistic.company.adapter.in.internal.mapper;

import com.logistic.common.internal.response.CompanyClientResponse;
import com.logistic.common.passport.model.Passport;
import com.logistic.company.application.port.in.query.FindCompanyQuery;
import com.logistic.company.application.port.in.query.ListCompanyQuery;
import com.logistic.company.domain.model.CompanyView;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyServiceMapper {

  FindCompanyQuery toFindQuery(Long companyId, Passport passport);

  ListCompanyQuery toListQuery(List<Long> companyIds, Passport passport);

  @Mapping(source = "hub.hubId", target = "hubId")
  CompanyClientResponse toCompanyResponse(CompanyView companyView);
}
