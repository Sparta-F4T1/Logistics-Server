package com.logistic.company.application.service;

import static com.logistic.company.common.TestFixtures.createGps;
import static com.logistic.company.common.TestFixtures.createHub;
import static com.logistic.company.common.TestFixtures.createPassport;
import static com.logistic.company.common.TestFixtures.createUserList;

import com.logistic.company.application.port.in.query.FindCompanyQuery;
import com.logistic.company.application.port.in.query.SearchCompanyQuery;
import com.logistic.company.application.port.out.CompanyInternalPort;
import com.logistic.company.application.port.out.CompanyQueryPersistencePort;
import com.logistic.company.domain.command.CompanyViewForCreate;
import com.logistic.company.domain.model.CompanyType;
import com.logistic.company.domain.model.CompanyView;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class CompanyQueryServiceTest {
  @Autowired
  private CompanyQueryService companyQueryService;
  @Autowired
  private CompanyQueryPersistencePort queryPersistencePort;
  @MockitoBean
  private CompanyInternalPort internalPort;

  @DisplayName("업체 상세조회가 성공한다.")
  @Test
  void findCompany_success() {
    // given
    Long companyId = 100L;
    FindCompanyQuery query = new FindCompanyQuery(saveCompanyView(companyId).getCompanyId(), createPassport());
    // when
    CompanyView companyView = companyQueryService.findCompany(query);
    // then
    Assertions.assertThat(companyView).isNotNull();
    Assertions.assertThat(companyView.getCompanyId()).isEqualTo(query.companyId());
  }

  @DisplayName("업체목록 조회가 성공한다.")
  @Test
  void search_success() {
    // given
    saveCompanyViews();
    Pageable pageable = PageRequest.of(0, 10);
    SearchCompanyQuery query = new SearchCompanyQuery(1L, "회사", CompanyType.PROVIDER, pageable);
    // when
    Page<CompanyView> search = companyQueryService.search(query);
    // then
    Assertions.assertThat(search).isNotNull();
    Assertions.assertThat(search.getTotalElements()).isEqualTo(10);
  }

  private CompanyView saveCompanyView(Long companyId) {
    CompanyViewForCreate forCreate = new CompanyViewForCreate(
        companyId, "회사", CompanyType.PROVIDER, createHub(1L), createGps(), createUserList());
    CompanyView companyView = CompanyView.create(forCreate);
    return queryPersistencePort.save(companyView);
  }

  private void saveCompanyViews() {
    for (long i = 0; i < 10; i++) {
      saveCompanyView(i);
    }
  }
}