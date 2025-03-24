package com.logistic.hub.application.port.in.command;

import com.logistic.common.passport.model.Passport;

public record RouteDeleteCommand(
    Long routeId,
    Passport passport
) {
}
