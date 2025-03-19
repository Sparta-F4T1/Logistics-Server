package com.logistic.hub.adapter.in.web.request;

import jakarta.validation.constraints.NotBlank;

public record HubUpdateRequest(
    @NotBlank String hubType,
    @NotBlank String hubName,
    @NotBlank String roadAddress,
    @NotBlank String jibunAddress
) {
}
