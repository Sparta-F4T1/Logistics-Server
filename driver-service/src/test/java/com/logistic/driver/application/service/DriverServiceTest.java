package com.logistic.driver.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.logistic.common.passport.model.Passport;
import com.logistic.common.passport.model.UserInfo;
import com.logistic.driver.application.port.in.command.CreateDriverCommand;
import com.logistic.driver.application.port.out.DriverInternalPort;
import com.logistic.driver.application.service.dto.HubInfo;
import com.logistic.driver.domain.Driver;
import com.logistic.driver.domain.DriverType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DriverServiceTest {

  @Autowired
  private DriverService driverService;
  @MockitoBean
  private DriverInternalPort internalPort;

  @DisplayName("배송담당자 생성이 성공한다.")
  @Test
  void create_success() {
    // given
    Passport passport = createPassport();
    HubInfo mockHubInfo = new HubInfo(1L);
    when(internalPort.findHub(1L, passport)).thenReturn(mockHubInfo);
    CreateDriverCommand command = new CreateDriverCommand("driver", DriverType.HUB, 1L, 2L, passport);
    // when
    Driver driver = driverService.createDriver(command);
    // then
    assertThat(driver).isNotNull();
    assertThat(driver.getId()).isEqualTo(command.driverId());
  }

  private Passport createPassport() {
    return Passport.create(UserInfo.create("master", "MASTER", 1L), null);
  }

}