package com.logistic.authcore.repository;

import java.time.Duration;

public interface AuthTokenRepository extends TokenRepository {

  /**
   * 사용자의 리프레시 토큰을 저장합니다.
   *
   * @param userId       사용자 ID
   * @param refreshToken 저장할 리프레시 토큰
   * @param duration     토큰을 저장할 기간
   */
  void saveRefreshToken(String userId, String refreshToken, Duration duration);

  /**
   * 사용자의 액세스 토큰을 저장합니다.
   *
   * @param userId      사용자 ID
   * @param accessToken 저장할 액세스 토큰
   * @param duration    토큰을 저장할 기간
   */
  void saveAccessToken(String userId, String accessToken, Duration duration);

  /**
   * 토큰을 블랙리스트에 추가합니다.
   *
   * @param tokenHash 블랙리스트에 추가할 토큰의 해시
   * @param duration  블랙리스트 유지 기간
   */
  void blacklistToken(String tokenHash, Duration duration);

  /**
   * 사용자의 액세스 토큰을 삭제합니다.
   *
   * @param userId 사용자 ID
   * @return 토큰이 삭제되었으면 true, 그렇지 않으면 false
   */
  boolean deleteAccessToken(String userId);

  /**
   * 사용자의 리프레시 토큰을 삭제합니다.
   *
   * @param userId 사용자 ID
   * @return 토큰이 삭제되었으면 true, 그렇지 않으면 false
   */
  boolean deleteRefreshToken(String userId);
}