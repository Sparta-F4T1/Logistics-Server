package com.logistic.company.application.service;

import com.logistic.company.application.port.in.command.CompanyCreateCommand;
import com.logistic.company.domain.Company;
import com.logistic.company.domain.CompanyType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CompanyServiceTest {

  @Autowired
  private CompanyService companyService;

  @DisplayName("업체 생성이 성공한다.")
  @Test
  void create_success() {
    // given
    CompanyCreateCommand command = new CompanyCreateCommand("이름", CompanyType.RECEIVER, "주소", "test", 1L);
    // when
    Company company = companyService.createCompany(command);
    // then
    Assertions.assertThat(company).isNotNull();
    Assertions.assertThat(company.getName()).isEqualTo("이름");
  }
}