package com.logistic.authcore.service;

import com.logistic.authcore.config.JwtProperties;
import com.logistic.authcore.repository.TokenVerificationRepository;
import com.logistic.authcore.util.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenVerificationServiceImpl implements TokenVerificationService {

  private final JwtProperties jwtProperties;
  private final JwtProvider jwtProvider;
  private final TokenVerificationRepository tokenVerificationRepository;

  @Override
  public Claims validateToken(String token) {
    if (isTokenBlacklisted(token)) {
      throw new InvalidTokenException("토큰이 블랙리스트에 있습니다.");
    }

    try {
      return jwtProvider.validateToken(token);
    } catch (JwtException e) {
      throw new InvalidTokenException("토큰이 유효하지 않습니다: " + e.getMessage(), e);
    }
  }

  @Override
  public Claims validateAccessToken(String accessToken) {
    if (isTokenBlacklisted(accessToken)) {
      throw new InvalidTokenException("액세스 토큰이 블랙리스트에 있습니다.");
    }

    try {
      Claims claims = jwtProvider.validateToken(accessToken);
      // TODO: 액세스 토큰 관련 추가 검증이 필요하면 구현
      return claims;
    } catch (JwtException e) {
      throw new InvalidTokenException("유효하지 않은 액세스 토큰: " + e.getMessage(), e);
    }
  }

  @Override
  public Claims validateRefreshToken(String refreshToken) {
    try {
      Claims claims = jwtProvider.validateToken(refreshToken);
      String userId = claims.getSubject();

      String storedToken = tokenVerificationRepository.getRefreshToken(userId);
      if (storedToken == null || !storedToken.equals(refreshToken)) {
        throw new InvalidTokenException(INVALID_REFRESH_TOKEN_MESSAGE);
      }

      return claims;
    } catch (JwtException e) {
      throw new InvalidTokenException("유효하지 않은 리프레시 토큰: " + e.getMessage(), e);
    }
  }

  @Override
  public boolean isTokenBlacklisted(String token) {
    String tokenHash = tokenVerificationRepository.hashToken(token);
    return tokenVerificationRepository.isTokenBlacklisted(tokenHash);
  }

  @Override
  public String getUserIdFromToken(String token) {
    try {
      Claims claims = jwtProvider.validateToken(token);
      return claims.getSubject();
    } catch (JwtException e) {
      throw new InvalidTokenException("유효하지 않은 토큰: " + e.getMessage(), e);
    }
  }
}