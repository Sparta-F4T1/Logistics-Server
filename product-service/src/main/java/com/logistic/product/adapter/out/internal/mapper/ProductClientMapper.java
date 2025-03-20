package com.logistic.product.adapter.out.internal.mapper;

import com.logistic.common.internal.response.CompanyClientResponse;
import com.logistic.product.application.service.dto.CompanyInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductClientMapper {
  CompanyInfo toCompanyInfo(CompanyClientResponse response);
}
