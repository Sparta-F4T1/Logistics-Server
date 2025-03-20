package com.logistic.auth.domain;

import com.logistic.auth.domain.exception.AuthServiceErrorCode;
import com.logistic.auth.domain.exception.AuthServiceException;
import com.logistic.auth.domain.vo.TokenCredential;
import com.logistic.auth.domain.vo.TokenId;
import com.logistic.auth.domain.vo.TokenType;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenPair {
  private final TokenId tokenId;
  private final TokenCredential accessTokenCredential;
  private final TokenCredential refreshTokenCredential;

  public static TokenPair create(
      String tokenIdValue,
      String accessTokenValue,
      String refreshTokenValue,
      String subject,
      Instant issuedAt) {

    Instant accessTokenExpiration = TokenType.ACCESS.calculateExpiration(issuedAt);
    Instant refreshTokenExpiration = TokenType.REFRESH.calculateExpiration(issuedAt);

    return TokenPair.create(
        tokenIdValue,
        accessTokenValue,
        refreshTokenValue,
        subject,
        issuedAt,
        accessTokenExpiration,
        issuedAt,
        refreshTokenExpiration
    );
  }

  private static TokenPair create(String tokenIdValue,
                                  String accessToken,
                                  String refreshToken,
                                  String subject,
                                  Instant accessTokenIssuedAt,
                                  Instant accessTokenExpiration,
                                  Instant refreshTokenIssuedAt,
                                  Instant refreshTokenExpiration) {
    TokenId tokenId = TokenId.of(tokenIdValue);

    TokenCredential accessTokenCredential = createAccessTokenCredential(
        accessToken, subject, accessTokenIssuedAt, accessTokenExpiration);

    TokenCredential refreshTokenCredential = createRefreshTokenCredential(
        refreshToken, subject, refreshTokenIssuedAt, refreshTokenExpiration);

    validateTokenPair(accessTokenCredential, refreshTokenCredential);

    return TokenPair.builder()
        .tokenId(tokenId)
        .accessTokenCredential(accessTokenCredential)
        .refreshTokenCredential(refreshTokenCredential)
        .build();
  }

  private static TokenCredential createAccessTokenCredential(
      String token, String subject, Instant issuedAt, Instant expiration) {
    return TokenCredential.accessTokenOf(token, subject, issuedAt, expiration);
  }

  private static TokenCredential createRefreshTokenCredential(
      String token, String subject, Instant issuedAt, Instant expiration) {
    return TokenCredential.refreshTokenOf(token, subject, issuedAt, expiration);
  }

  private static void validateTokenPair(TokenCredential accessTokenCredential, TokenCredential refreshTokenCredential) {
    if (!accessTokenCredential.isAccessToken()) {
      throw new AuthServiceException(AuthServiceErrorCode.INVALID_TOKEN_TYPE);
    }
    if (!refreshTokenCredential.isRefreshToken()) {
      throw new AuthServiceException(AuthServiceErrorCode.INVALID_TOKEN_TYPE);
    }

    String accessSubject = accessTokenCredential.getClaims().getSubject().value();
    String refreshSubject = refreshTokenCredential.getClaims().getSubject().value();

    if (!accessSubject.equals(refreshSubject)) {
      throw new AuthServiceException(AuthServiceErrorCode.INVALID_TOKEN_PAIR);
    }
  }
}