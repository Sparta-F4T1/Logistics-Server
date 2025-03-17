package com.logistic.company.application.service;

import com.logistic.company.application.port.in.query.CompanyFindQuery;
import com.logistic.company.application.port.in.query.CompanySearchQuery;
import com.logistic.company.application.port.out.CompanyPersistencePort;
import com.logistic.company.application.port.out.GpsClientPort;
import com.logistic.company.domain.Company;
import com.logistic.company.domain.CompanyType;
import com.logistic.company.domain.vo.Address;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CompanyQueryServiceTest {
  @Autowired
  private CompanyQueryService companyQueryService;
  @Autowired
  private CompanyPersistencePort companyPersistencePort;
  @MockitoBean
  private GpsClientPort gpsClientPort;

  @DisplayName("업체 상세조회가 성공한다.")
  @Test
  void findCompany_success() {
    // given
    Company saved = saveCompany();
    CompanyFindQuery query = new CompanyFindQuery(saved.getId());
    // when
    Company company = companyQueryService.findCompany(query);
    // then
    Assertions.assertThat(company).isNotNull();
    Assertions.assertThat(company.getId()).isEqualTo(saved.getId());
  }

  @DisplayName("업체목록 조회가 성공한다.")
  @Test
  void search_success() {
    // given
    saveCompanies();
    Pageable pageable = PageRequest.of(0, 10);
    CompanySearchQuery query = new CompanySearchQuery(1L, "회사", CompanyType.PROVIDER, pageable);
    // when
    Page<Company> search = companyQueryService.search(query);
    // then
    Assertions.assertThat(search).isNotNull();
    Assertions.assertThat(search.getTotalElements()).isEqualTo(10);
  }

  private Company saveCompany() {
    Address address = new Address("road", "jibun", 30.0, 40.0);
    Company company = Company.create("회사이름", CompanyType.PROVIDER, address, "매니저", 1L);
    return companyPersistencePort.save(company);
  }

  private void saveCompanies() {
    for (int i = 0; i < 10; i++) {
      saveCompany();
    }
  }
}