package com.logistic.company.adaptor.in.web.mapper;

import com.logistic.company.adaptor.in.web.request.CompanyCreateRequest;
import com.logistic.company.adaptor.in.web.response.CompanyResponseDto;
import com.logistic.company.application.port.in.command.CompanyCreateCommand;
import com.logistic.company.domain.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyWebMapper {

  CompanyCreateCommand toCreateCommand(CompanyCreateRequest request);

  @Mapping(source = "id", target = "companyId")
  CompanyResponseDto toResponseDto(Company company);
}