package com.logistic.company.adaptor.out.persistence.mapper;

import com.logistic.company.adaptor.out.persistence.CompanyEntity;
import com.logistic.company.domain.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyPersistenceMapper {
  Company toDomain(CompanyEntity companyEntity);

  CompanyEntity toEntity(Company company);
}