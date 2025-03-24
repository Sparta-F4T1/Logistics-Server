package com.logistic.driver.application.port.in.command;

import com.logistic.common.passport.model.Passport;
import java.util.List;

public record GetHubDriverCommand(
    List<hubRoute> hubRouteList,
    Passport passport
) {
  public record hubRoute(
      Long departHubId,
      Long arrivalHubId) {
  }
}
