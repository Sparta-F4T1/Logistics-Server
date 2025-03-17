package com.logistic.authcore.service;

import com.logistic.authcore.config.JwtProperties;
import com.logistic.authcore.repository.ReactiveTokenVerificationRepository;
import com.logistic.authcore.util.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveTokenVerificationService implements TokenVerificationService {

  private final JwtProperties jwtProperties;
  private final JwtProvider jwtProvider;
  private final ReactiveTokenVerificationRepository reactiveTokenRepository;

  @Override
  public Claims validateToken(String token) {
    return validateTokenReactive(token).block();
  }

  @Override
  public Claims validateAccessToken(String accessToken) {
    return validateAccessTokenReactive(accessToken).block();
  }

  @Override
  public Claims validateRefreshToken(String refreshToken) {
    return validateRefreshTokenReactive(refreshToken).block();
  }

  @Override
  public boolean isTokenBlacklisted(String token) {
    return isTokenBlacklistedReactive(token).block();
  }

  @Override
  public String getUserIdFromToken(String token) {
    return getUserIdFromTokenReactive(token).block();
  }

  public Mono<Claims> validateTokenReactive(String token) {
    return isTokenBlacklistedReactive(token)
        .flatMap(isBlacklisted -> {
          if (isBlacklisted) {
            return Mono.error(new InvalidTokenException("토큰이 블랙리스트에 있습니다."));
          }

          return Mono.fromCallable(() -> {
            try {
              return jwtProvider.validateToken(token);
            } catch (JwtException e) {
              throw new InvalidTokenException("유효하지 않은 JWT 토큰: " + e.getMessage(), e);
            }
          });
        });
  }

  public Mono<Claims> validateAccessTokenReactive(String accessToken) {
    return isTokenBlacklistedReactive(accessToken)
        .flatMap(isBlacklisted -> {
          if (isBlacklisted) {
            return Mono.error(new InvalidTokenException("액세스 토큰이 블랙리스트에 있습니다."));
          }

          return Mono.fromCallable(() -> {
            try {
              return jwtProvider.validateToken(accessToken);
            } catch (JwtException e) {
              throw new InvalidTokenException("유효하지 않은 액세스 토큰: " + e.getMessage(), e);
            }
          });
        });
  }

  public Mono<Claims> validateRefreshTokenReactive(String refreshToken) {
    return Mono.fromCallable(() -> jwtProvider.validateToken(refreshToken))
        .flatMap(claims -> {
          String userId = claims.getSubject();
          return reactiveTokenRepository.getRefreshTokenReactive(userId)
              .filter(storedToken -> storedToken.equals(refreshToken))
              .switchIfEmpty(Mono.error(new InvalidTokenException(INVALID_REFRESH_TOKEN_MESSAGE)))
              .thenReturn(claims);
        })
        .onErrorMap(e -> {
          if (e instanceof JwtException) {
            return new InvalidTokenException("유효하지 않은 리프레시 토큰: " + e.getMessage(), e);
          }
          return e;
        });
  }

  public Mono<Boolean> isTokenBlacklistedReactive(String token) {
    String tokenHash = reactiveTokenRepository.hashToken(token);
    return reactiveTokenRepository.isTokenBlacklistedReactive(tokenHash);
  }

  public Mono<String> getUserIdFromTokenReactive(String token) {
    return Mono.fromCallable(() -> {
      try {
        Claims claims = jwtProvider.validateToken(token);
        return claims.getSubject();
      } catch (JwtException e) {
        throw new InvalidTokenException("유효하지 않은 토큰: " + e.getMessage(), e);
      }
    });
  }
}