package com.logistic.auth.adapter.in.web.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(@NotBlank(message = "액세스 토큰이 필요합니다.") String accessToken,
                                  @NotBlank(message = "리프레쉬 토큰이 필요합니다.") String refreshToken) {
}
