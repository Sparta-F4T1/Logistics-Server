package com.logistic.auth.domain.service;

import com.logistic.auth.domain.vo.TokenId;
import com.logistic.auth.domain.vo.UserId;
import java.time.Instant;

public record TokenValidationResult(
    TokenId tokenId,
    UserId userId,
    Instant issuedAt,
    Instant expiration
) {
  public static TokenValidationResult valid(
      TokenId tokenId,
      UserId userId,
      Instant issuedAt,
      Instant expiration
  ) {
    return new TokenValidationResult(tokenId, userId, issuedAt, expiration);
  }
}