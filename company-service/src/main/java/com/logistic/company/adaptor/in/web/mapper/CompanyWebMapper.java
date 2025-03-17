package com.logistic.company.adaptor.in.web.mapper;

import com.logistic.company.adaptor.in.web.request.CompanyCreateRequest;
import com.logistic.company.adaptor.in.web.request.CompanySearchRequest;
import com.logistic.company.adaptor.in.web.request.CompanyUpdateRequest;
import com.logistic.company.adaptor.in.web.response.CompanyResponse;
import com.logistic.company.application.port.in.command.CompanyCreateCommand;
import com.logistic.company.application.port.in.command.CompanyDeleteCommand;
import com.logistic.company.application.port.in.command.CompanyUpdateCommand;
import com.logistic.company.application.port.in.query.CompanyFindQuery;
import com.logistic.company.application.port.in.query.CompanySearchQuery;
import com.logistic.company.domain.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Pageable;

@Mapper(componentModel = "spring")
public interface CompanyWebMapper {

  CompanyCreateCommand toCreateCommand(CompanyCreateRequest request);

  @Mapping(source = "id", target = "companyId")
  CompanyResponse toResponse(Company company);

  CompanyUpdateCommand toUpdateCommand(Long companyId, CompanyUpdateRequest request);

  CompanyDeleteCommand toDeleteCommand(Long companyId);

  CompanyFindQuery toFindQuery(Long companyId);

  CompanySearchQuery toSearchQuery(CompanySearchRequest request, Pageable pageable);
}