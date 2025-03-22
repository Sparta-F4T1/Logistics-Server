package com.logistic.driver.domain.model;

import com.logistic.driver.domain.command.DriverViewForCreate;
import com.logistic.driver.domain.model.vo.DriverStatus;
import com.logistic.driver.domain.model.vo.DriverType;
import com.logistic.driver.domain.model.vo.Hub;
import com.logistic.driver.domain.model.vo.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DriverView {
  private User driver;
  private DriverType type;
  private DriverStatus status;
  private Hub hub;
  private Boolean isDeleted;

  public static DriverView create(final DriverViewForCreate forCreate) {
    return DriverView.builder()
        .driver(forCreate.user())
        .type(forCreate.type())
        .status(forCreate.status())
        .hub(forCreate.hub())
        .isDeleted(false)
        .build();
  }
}
