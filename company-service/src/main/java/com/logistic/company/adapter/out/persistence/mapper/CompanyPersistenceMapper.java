package com.logistic.company.adapter.out.persistence.mapper;

import com.logistic.company.adapter.out.persistence.model.CompanyEntity;
import com.logistic.company.adapter.out.persistence.model.CompanyViewEntity;
import com.logistic.company.domain.model.Company;
import com.logistic.company.domain.model.CompanyView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyPersistenceMapper {
  Company toDomain(CompanyEntity companyEntity);

  CompanyEntity toEntity(Company company);

  CompanyView toDomain(CompanyViewEntity companyViewEntity);

  CompanyViewEntity toEntity(CompanyView companyView);
}