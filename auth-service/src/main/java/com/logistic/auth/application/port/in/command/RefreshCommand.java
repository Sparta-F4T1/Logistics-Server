package com.logistic.auth.application.port.in.command;

public record RefreshCommand(
    String accessToken,
    String refreshToken
) {
}