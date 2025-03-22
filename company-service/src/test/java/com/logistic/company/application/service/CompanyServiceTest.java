package com.logistic.company.application.service;

import static com.logistic.company.common.TestFixtures.createHub;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.logistic.common.passport.model.Passport;
import com.logistic.common.passport.model.UserInfo;
import com.logistic.company.application.port.in.command.CreateCompanyCommand;
import com.logistic.company.application.port.in.command.DeleteCompanyCommand;
import com.logistic.company.application.port.in.command.UpdateCompanyCommand;
import com.logistic.company.application.port.out.CompanyCommandPersistencePort;
import com.logistic.company.application.port.out.CompanyInternalPort;
import com.logistic.company.domain.command.CompanyForCreate;
import com.logistic.company.domain.model.Company;
import com.logistic.company.domain.model.CompanyType;
import com.logistic.company.domain.model.vo.Gps;
import com.logistic.company.domain.model.vo.User;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CompanyServiceTest {

  @Autowired
  private CompanyCommandService companyService;
  @Autowired
  private CompanyCommandPersistencePort persistencePort;
  @MockitoBean
  private CompanyInternalPort internalPort;

  @DisplayName("업체 생성이 성공한다.")
  @Test
  void create_success() {
    // given
    CreateCompanyCommand command = new CreateCompanyCommand(
        "이름", CompanyType.RECEIVER,
        "주소", 1L, List.of("user1", "user2", "user3"), createPassport());
    when(internalPort.findGps(anyString())).thenReturn(createGps());
    when(internalPort.findHub(anyLong())).thenReturn(createHub(command.hubId()));
    when(internalPort.findUserList(any())).thenReturn(createUserList());
    // when
    Company company = companyService.createCompany(command);
    //then
    Assertions.assertThat(company).isNotNull();
    verify(internalPort).findGps(anyString());
    verify(internalPort).findHub(anyLong());
    verify(internalPort).findUserList(any());
  }

  @DisplayName("업체 수정이 성공한다.")
  @Test
  void update_success() {
    // given
    UpdateCompanyCommand command = new UpdateCompanyCommand(
        saveCompany().getId(), "업데이트", CompanyType.RECEIVER,
        "주소", 1L, List.of("user1", "user2", "user3"), createPassport());
    when(internalPort.findGps(anyString())).thenReturn(createGps());
    when(internalPort.findHub(anyLong())).thenReturn(createHub(command.hubId()));
    when(internalPort.findUserList(any())).thenReturn(createUserList());
    // when
    Company updated = companyService.updateCompany(command);
    // then
    Assertions.assertThat(updated).isNotNull();
    Assertions.assertThat(updated.getName()).isEqualTo(command.name());
  }

  @DisplayName("업체 삭제가 성공한다.")
  @Test
  void softDelete_success() {
    // given
    DeleteCompanyCommand command = new DeleteCompanyCommand(saveCompany().getId(), createPassport());
    // when
    companyService.deleteCompany(command);
    // then
    Company deleted = persistencePort.findById(command.companyId());
    Assertions.assertThat(deleted).isNotNull();
    Assertions.assertThat(deleted.getIsDeleted()).isTrue();
  }

  private Company saveCompany() {
    CompanyForCreate forCreate = new CompanyForCreate(
        "이름", CompanyType.PROVIDER, createHub(1L), createGps(), createUserList());
    Company company = Company.create(forCreate);
    return persistencePort.save(company);
  }


  private Gps createGps() {
    return new Gps("road", "jibun", 30.0, 40.0);
  }

  private User createUser(String userId) {
    return new User(userId, "이름");
  }

  private List<User> createUserList() {
    List<User> userList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      userList.add(createUser("user" + i));
    }
    return userList;
  }

  private Passport createPassport() {
    return new Passport(createUserInfo(), null);
  }

  private UserInfo createUserInfo() {
    return new UserInfo("master", "MASTER_ADMIN", null);
  }
}