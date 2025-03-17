package com.logistic.authcore.service;

import com.logistic.authcore.config.JwtProperties;
import com.logistic.authcore.repository.AuthTokenRepository;
import com.logistic.authcore.repository.TokenVerificationRepository;
import com.logistic.authcore.util.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthTokenServiceImpl implements AuthTokenService {

  private final JwtProperties jwtProperties;
  private final JwtProvider jwtProvider;
  private final AuthTokenRepository authTokenRepository;
  private final TokenVerificationRepository tokenVerificationRepository;

  @Override
  public String generateAccessToken(String userId) {
    return jwtProvider.createAccessToken(userId);
  }

  @Override
  public String generateRefreshToken(String userId) {
    return jwtProvider.createRefreshToken(userId);
  }

  @Override
  public List<String> generateTokenPair(String userId) {
    String accessToken = generateAccessToken(userId);
    String refreshToken = generateRefreshToken(userId);

    storeRefreshToken(userId, refreshToken);

    return new ArrayList<>(List.of(accessToken, refreshToken));
  }

  @Override
  public String refreshAccessToken(String refreshToken) {
    try {
      Claims claims = jwtProvider.validateToken(refreshToken);
      String userId = claims.getSubject();

      String storedToken = tokenVerificationRepository.getRefreshToken(userId);
      if (storedToken == null || !storedToken.equals(refreshToken)) {
        throw new InvalidTokenException(INVALID_REFRESH_TOKEN_MESSAGE);
      }

      return generateAccessToken(userId);
    } catch (JwtException e) {
      throw new InvalidTokenException("유효하지 않은 리프레시 토큰: " + e.getMessage(), e);
    }
  }

  @Override
  public List<String> refreshTokenPair(String refreshToken) {
    try {
      Claims claims = jwtProvider.validateToken(refreshToken);
      String userId = claims.getSubject();

      String storedToken = tokenVerificationRepository.getRefreshToken(userId);
      if (storedToken == null || !storedToken.equals(refreshToken)) {
        throw new InvalidTokenException(INVALID_REFRESH_TOKEN_MESSAGE);
      }

      String newAccessToken = generateAccessToken(userId);
      String newRefreshToken = generateRefreshToken(userId);

      storeRefreshToken(userId, newRefreshToken);

      return new ArrayList<>(List.of(newAccessToken, newRefreshToken));
    } catch (JwtException e) {
      throw new InvalidTokenException("유효하지 않은 리프레시 토큰: " + e.getMessage(), e);
    }
  }

  @Override
  public void revokeTokens(String accessToken, String refreshToken) {
    try {
      Claims claims = jwtProvider.validateToken(accessToken);
      String userId = claims.getSubject();

      Date expiration = claims.getExpiration();
      long timeToLive = Math.max(0, expiration.getTime() - System.currentTimeMillis());
      Duration duration = jwtProperties.getBlacklistTokenDuration();
      if (timeToLive > 0) {
        duration = Duration.ofMillis(Math.min(timeToLive, duration.toMillis()));
      }
      blacklistToken(accessToken, duration);

      if (refreshToken != null) {
        authTokenRepository.deleteRefreshToken(userId);
      }
    } catch (JwtException e) {
      throw new InvalidTokenException("유효하지 않은 액세스 토큰: " + e.getMessage(), e);
    }
  }

  @Override
  public boolean blacklistToken(String token) {
    try {
      Claims claims = jwtProvider.validateToken(token);
      Date expiration = claims.getExpiration();
      long timeToLive = Math.max(0, expiration.getTime() - System.currentTimeMillis());
      Duration duration = Duration.ofMillis(timeToLive);

      String tokenHash = authTokenRepository.hashToken(token);
      authTokenRepository.blacklistToken(tokenHash, duration);
      return true;
    } catch (JwtException e) {
      log.error("토큰 블랙리스트 추가 중 오류: {}", e.getMessage());
      return false;
    }
  }

  @Override
  public boolean blacklistToken(String token, Duration duration) {
    try {
      String tokenHash = authTokenRepository.hashToken(token);
      authTokenRepository.blacklistToken(tokenHash, duration);
      return true;
    } catch (Exception e) {
      log.error("토큰 블랙리스트 추가 중 오류: {}", e.getMessage());
      return false;
    }
  }

  @Override
  public void storeAccessToken(String userId, String accessToken) {
    try {
      Claims claims = jwtProvider.validateToken(accessToken);
      Date expiration = claims.getExpiration();
      long timeToLive = Math.max(0, expiration.getTime() - System.currentTimeMillis());
      Duration storageDuration = jwtProperties.getAccessTokenDuration();
      if (timeToLive > 0) {
        storageDuration = Duration.ofMillis(Math.min(timeToLive, storageDuration.toMillis()));
      }

      authTokenRepository.saveAccessToken(userId, accessToken, storageDuration);
    } catch (JwtException e) {
      log.error("액세스 토큰 저장 중 오류: {}", e.getMessage());
      throw new InvalidTokenException("액세스 토큰 저장 실패: " + e.getMessage(), e);
    }
  }

  @Override
  public void storeAccessToken(String userId, String accessToken, Duration duration) {
    Duration storageDuration = duration != null ? duration : jwtProperties.getAccessTokenDuration();
    authTokenRepository.saveAccessToken(userId, accessToken, storageDuration);
  }

  @Override
  public void storeRefreshToken(String userId, String refreshToken) {
    try {
      Claims claims = jwtProvider.validateToken(refreshToken);
      Date expiration = claims.getExpiration();
      long timeToLive = Math.max(0, expiration.getTime() - System.currentTimeMillis());
      Duration storageDuration = jwtProperties.getRefreshTokenStorageDuration();
      if (timeToLive > 0) {
        storageDuration = Duration.ofMillis(Math.min(timeToLive, storageDuration.toMillis()));
      }

      authTokenRepository.saveRefreshToken(userId, refreshToken, storageDuration);
    } catch (JwtException e) {
      log.error("리프레시 토큰 저장 중 오류: {}", e.getMessage());
      throw new InvalidTokenException("리프레시 토큰 저장 실패: " + e.getMessage(), e);
    }
  }

  @Override
  public void storeRefreshToken(String userId, String refreshToken, Duration duration) {
    Duration storageDuration = duration != null ? duration : jwtProperties.getRefreshTokenStorageDuration();
    authTokenRepository.saveRefreshToken(userId, refreshToken, storageDuration);
  }

  @Override
  public void storeTokenPair(String userId, String accessToken, String refreshToken) {
    storeAccessToken(userId, accessToken);
    storeRefreshToken(userId, refreshToken);
  }

  @Override
  public void storeTokenPair(String userId, String accessToken, String refreshToken, Duration duration) {
    storeAccessToken(userId, accessToken, duration);
    storeRefreshToken(userId, refreshToken, duration);
  }
}