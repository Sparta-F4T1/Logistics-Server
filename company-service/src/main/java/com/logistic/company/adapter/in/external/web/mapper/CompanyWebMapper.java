package com.logistic.company.adapter.in.external.web.mapper;

import com.logistic.common.passport.model.Passport;
import com.logistic.company.adapter.in.external.web.request.CreateCompanyRequest;
import com.logistic.company.adapter.in.external.web.request.SearchCompanyRequest;
import com.logistic.company.adapter.in.external.web.request.UpdateCompanyRequest;
import com.logistic.company.adapter.in.external.web.response.CommandCompanyResponse;
import com.logistic.company.adapter.in.external.web.response.QueryCompanyResponse;
import com.logistic.company.application.port.in.command.CreateCompanyCommand;
import com.logistic.company.application.port.in.command.DeleteCompanyCommand;
import com.logistic.company.application.port.in.command.UpdateCompanyCommand;
import com.logistic.company.application.port.in.query.FindCompanyQuery;
import com.logistic.company.application.port.in.query.SearchCompanyQuery;
import com.logistic.company.domain.model.Company;
import com.logistic.company.domain.model.CompanyView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Pageable;

@Mapper(componentModel = "spring")
public interface CompanyWebMapper {

  CreateCompanyCommand toCreateCommand(CreateCompanyRequest request, Passport passport);

  UpdateCompanyCommand toUpdateCommand(Long companyId, UpdateCompanyRequest request, Passport passport);

  DeleteCompanyCommand toDeleteCommand(Long companyId, Passport passport);

  FindCompanyQuery toFindQuery(Long companyId, Passport passport);

  SearchCompanyQuery toSearchQuery(SearchCompanyRequest request, Pageable pageable, Passport passport);

  @Mapping(source = "id", target = "companyId")
  CommandCompanyResponse toResponse(Company company);

  QueryCompanyResponse toQueryResponse(CompanyView companyView);
}