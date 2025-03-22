package com.logistic.company.application.port.in;

import com.logistic.company.application.port.in.query.FindCompanyQuery;
import com.logistic.company.application.port.in.query.ListCompanyQuery;
import com.logistic.company.application.port.in.query.SearchCompanyQuery;
import com.logistic.company.domain.model.CompanyView;
import java.util.List;
import org.springframework.data.domain.Page;

public interface CompanyQueryUseCase {
  CompanyView findCompany(FindCompanyQuery query);

  List<CompanyView> findCompanyList(ListCompanyQuery query);

  Page<CompanyView> search(SearchCompanyQuery query);
}
