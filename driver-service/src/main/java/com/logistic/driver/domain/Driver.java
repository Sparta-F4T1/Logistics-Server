package com.logistic.driver.domain;

import com.logistic.driver.domain.command.DriverForCreate;
import com.logistic.driver.domain.command.DriverForUpdate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Driver {
  private String id;
  private DriverType type;
  private Long departHubId;
  private Long arrivalHubId;
  private Boolean isDeleted;

  public static Driver create(final DriverForCreate forCreate) {
    return Driver.builder()
        .id(forCreate.driverId())
        .type(forCreate.type())
        .departHubId(forCreate.departHubId())
        .arrivalHubId(forCreate.arrivalHubId())
        .isDeleted(false)
        .build();
  }

  public void update(final DriverForUpdate forUpdate) {
    this.type = forUpdate.type();
    this.departHubId = forUpdate.departHubId();
    this.arrivalHubId = forUpdate.arrivalHubId();
  }

  public void delete() {
    isDeleted = true;
  }
}
