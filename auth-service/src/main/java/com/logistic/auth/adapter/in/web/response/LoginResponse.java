package com.logistic.auth.adapter.in.web.response;

public record LoginResponse(
    String accessToken,
    String refreshToken
) {
}