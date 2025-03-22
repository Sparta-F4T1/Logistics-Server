package com.logistic.driver.domain.model;

import com.logistic.driver.domain.command.DriverForCreate;
import com.logistic.driver.domain.command.DriverForUpdate;
import com.logistic.driver.domain.model.vo.DriverStatus;
import com.logistic.driver.domain.model.vo.DriverType;
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
  private DriverStatus status;
  private Long hubId;
  private Boolean isDeleted;

  public static Driver create(final DriverForCreate forCreate) {
    return Driver.builder()
        .id(forCreate.user().driverId())
        .type(forCreate.type())
        .status(forCreate.status())
        .hubId(forCreate.departHub().hubId())
        .isDeleted(false)
        .build();
  }

  public void update(final DriverForUpdate forUpdate) {
    this.type = forUpdate.type();
    this.status = forUpdate.status();
    this.hubId = forUpdate.departHub().hubId();
  }

  public void delete() {
    status = DriverStatus.UNAVAILABLE;
    isDeleted = true;
  }
}
