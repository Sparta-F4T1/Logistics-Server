package com.logistic.product.application.port.out;

import com.logistic.product.domain.vo.Company;
import com.logistic.product.domain.vo.Hub;

public interface ProductInternalPort {
  Company findCompany(Long companyId);

  Hub findHub(Long hubId);
}
