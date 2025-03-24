package com.logistic.product.adapter.out.internal.mapper;

import com.logistic.common.internal.response.CompanyClientResponse;
import com.logistic.common.internal.response.HubClientResponse;
import com.logistic.product.domain.vo.Company;
import com.logistic.product.domain.vo.Hub;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductClientMapper {
  Company toCompany(CompanyClientResponse response);

  Hub toHub(HubClientResponse response);
}
