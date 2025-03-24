package com.logistic.hub.application.port.in.command;

import com.logistic.common.passport.model.Passport;
import jakarta.validation.constraints.NotNull;

public record HubUpdateCommand(
    Long hubId,
    @NotNull String hubType,
    @NotNull String hubName,
    @NotNull String roadAddress,
    @NotNull String jibunAddress,
    Passport passport
) {
}
