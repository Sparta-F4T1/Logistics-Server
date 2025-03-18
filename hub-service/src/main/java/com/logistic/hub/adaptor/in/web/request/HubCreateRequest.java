package com.logistic.hub.adaptor.in.web.request;

import jakarta.validation.constraints.NotBlank;

public record HubCreateRequest(
    @NotBlank String hubType,
    @NotBlank String hubName,
    @NotBlank String roadAddress,
    @NotBlank String jibunAddress
) {
}
