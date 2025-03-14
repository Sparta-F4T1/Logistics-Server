package com.logistic.company.application.service;

import com.logistic.company.application.port.in.command.CompanyCreateCommand;
import com.logistic.company.application.port.out.HubServicePort;
import com.logistic.company.domain.Company;
import com.logistic.company.domain.CompanyType;
import com.logistic.company.domain.vo.Address;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CompanyServiceTest {

  @Autowired
  private CompanyService companyService;

  @MockitoBean
  private HubServicePort hubServicePort;

  @DisplayName("업체 생성이 성공한다.")
  @Test
  void create_success() {
    // given
    CompanyCreateCommand command = new CompanyCreateCommand("이름", CompanyType.RECEIVER, "주소", "test", 1L);

    Address mockAddress = new Address("road", "jibun", 30.0, 40.0);
    Mockito.when(hubServicePort.getAddress("주소")).thenReturn(mockAddress);

    Company company = companyService.createCompany(command);

    Assertions.assertThat(company).isNotNull();
    Assertions.assertThat(company.getName()).isEqualTo("이름");
  }
}