package com.logistic.hub.domain.command;

import jakarta.validation.constraints.NotNull;

public record AddressCommand(
    @NotNull String road,
    @NotNull String jibun,
    @NotNull Double latitude,
    @NotNull Double longitude
) {
}
