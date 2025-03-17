package com.logistic.company.application.service;

import com.logistic.company.application.port.in.command.CompanyCreateCommand;
import com.logistic.company.application.port.in.command.CompanyDeleteCommand;
import com.logistic.company.application.port.in.command.CompanyUpdateCommand;
import com.logistic.company.application.port.out.CompanyPersistencePort;
import com.logistic.company.application.port.out.GpsClientPort;
import com.logistic.company.domain.Company;
import com.logistic.company.domain.CompanyType;
import com.logistic.company.domain.vo.Address;
import java.util.Optional;
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
  @Autowired
  private CompanyPersistencePort companyPersistencePort;
  @MockitoBean
  private GpsClientPort gpsClientPort;

  @DisplayName("업체 생성이 성공한다.")
  @Test
  void create_success() {
    // given
    CompanyCreateCommand command = new CompanyCreateCommand("이름", CompanyType.RECEIVER, "주소", "test", 1L);
    Address mockAddress = new Address("road", "jibun", 30.0, 40.0);
    Mockito.when(gpsClientPort.getAddress("주소")).thenReturn(mockAddress);

    // when
    Company company = companyService.createCompany(command);

    //then
    Assertions.assertThat(company).isNotNull();
    Assertions.assertThat(company.getName()).isEqualTo("이름");
  }

  @DisplayName("업체 정보수정이 성공한다.")
  @Test
  void update_success() {
    // given
    Company saved = saveCompany();
    CompanyUpdateCommand command = new CompanyUpdateCommand(saved.getId(), "업데이트", CompanyType.RECEIVER, "주소", "매니저",
        1L);
    Address mockAddress = new Address("road", "jibun", 30.0, 40.0);
    Mockito.when(gpsClientPort.getAddress("주소")).thenReturn(mockAddress);
    // when
    Company updated = companyService.updateCompany(command);
    // then
    Assertions.assertThat(updated).isNotNull();
    Assertions.assertThat(updated.getName()).isEqualTo(command.name());
  }

  @DisplayName("업체 소프트삭제가 성공한다.")
  @Test
  void softDelete_success() {
    // given
    Company saved = saveCompany();
    CompanyDeleteCommand command = new CompanyDeleteCommand(saved.getId());
    // when
    companyService.deleteCompany(command);
    Optional<Company> company = companyPersistencePort.findById(saved.getId());
    Company deleted = company.orElse(null);
    // then
    Assertions.assertThat(deleted).isNotNull();
    Assertions.assertThat(deleted.getIsDeleted()).isTrue();
  }

  private Company saveCompany() {
    Address address = new Address("road", "jibun", 30.0, 40.0);
    Company company = Company.create("회사이름", CompanyType.PROVIDER, address, "매니저", 1L);
    return companyPersistencePort.save(company);
  }
}