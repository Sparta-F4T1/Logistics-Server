package com.logistic.hub.application.port.in.command;

import com.logistic.common.passport.model.Passport;
import jakarta.validation.constraints.NotNull;

public record RouteCreateCommand(
    @NotNull Long departHubId,
    @NotNull Long arrivalHubId,
    Passport passport
) {
}
