package com.logistic.product.application.port.out;

import com.logistic.common.passport.model.Passport;
import com.logistic.product.application.service.dto.CompanyInfo;

public interface ProductInternalPort {
  CompanyInfo findCompany(Long companyId, Passport passport);
}
