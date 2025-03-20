package com.logistic.auth.adapter.out.support.jwt;

import com.logistic.auth.adapter.out.support.jwt.util.JwtProvider;
import com.logistic.auth.adapter.out.support.jwt.util.JwtValidator;
import com.logistic.auth.application.port.out.support.jwt.AuthJwtPort;
import com.logistic.auth.domain.TokenPair;
import com.logistic.auth.domain.vo.UserId;
import com.logistic.common.annotation.Adapter;
import java.time.Instant;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class AuthJwtAdapter implements AuthJwtPort {
  private final JwtProvider jwtProvider;
  private final JwtValidator jwtValidator;

  @Override
  public TokenPair createTokenPair(String tokenIdValue, UserId subject) {
    String accessTokenValue = jwtProvider.createAccessToken(
        subject.value(),
        tokenIdValue
    );
    String refreshTokenValue = jwtProvider.createRefreshToken(
        subject.value(),
        tokenIdValue
    );

    Instant issuedAt = Instant.now();
    return TokenPair.create(
        tokenIdValue,
        accessTokenValue,
        refreshTokenValue,
        subject.value(),
        issuedAt
    );
  }
}