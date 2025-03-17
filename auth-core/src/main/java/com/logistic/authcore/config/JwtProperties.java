package com.logistic.authcore.config;

import java.time.Duration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
  /**
   * JWT 서명에 사용되는 비밀 키
   */
  private String secret;

  /**
   * 액세스 토큰 만료 시간 (밀리초)
   */
  private long accessTokenExpirationMs;

  /**
   * 리프레시 토큰 만료 시간 (밀리초)
   */
  private long refreshTokenExpirationMs;

  /**
   * 블랙리스트에 토큰을 유지하는 시간 (밀리초) 기본적으로 액세스 토큰 만료 시간과 동일합니다.
   */
  private long blacklistTokenExpirationMs;

  /**
   * 저장소에 리프레시 토큰을 유지하는 시간 (밀리초) 기본적으로 리프레시 토큰 만료 시간과 동일합니다.
   */
  private long refreshTokenStorageExpirationMs;

  /**
   * 액세스 토큰 만료 시간을 Duration 형태로 반환
   */
  public Duration getAccessTokenDuration() {
    return Duration.ofMillis(accessTokenExpirationMs);
  }

  /**
   * 리프레시 토큰 만료 시간을 Duration 형태로 반환
   */
  public Duration getRefreshTokenDuration() {
    return Duration.ofMillis(refreshTokenExpirationMs);
  }

  /**
   * 블랙리스트에 토큰을 유지하는 시간을 Duration 형태로 반환 값이 설정되지 않은 경우 액세스 토큰 만료 시간을 사용합니다.
   */
  public Duration getBlacklistTokenDuration() {
    return blacklistTokenExpirationMs > 0
        ? Duration.ofMillis(blacklistTokenExpirationMs)
        : getAccessTokenDuration();
  }

  /**
   * 저장소에 리프레시 토큰을 유지하는 시간을 Duration 형태로 반환 값이 설정되지 않은 경우 리프레시 토큰 만료 시간을 사용합니다.
   */
  public Duration getRefreshTokenStorageDuration() {
    return refreshTokenStorageExpirationMs > 0
        ? Duration.ofMillis(refreshTokenStorageExpirationMs)
        : getRefreshTokenDuration();
  }
}