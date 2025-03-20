package com.logistic.auth.adapter.in.web.response;

public record RefreshTokenResponse(
    String accessToken,
    String refreshToken
) {
}