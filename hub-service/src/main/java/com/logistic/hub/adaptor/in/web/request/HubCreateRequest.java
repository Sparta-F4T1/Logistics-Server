package com.logistic.hub.adaptor.in.web.request;

import jakarta.validation.constraints.NotNull;

public record HubCreateRequest(
    @NotNull String hubType,
    @NotNull String hubName,
    @NotNull String roadAddress,
    @NotNull String jibunAddress
) {
}
