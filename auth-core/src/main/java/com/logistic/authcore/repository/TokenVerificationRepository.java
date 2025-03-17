package com.logistic.authcore.repository;

public interface TokenVerificationRepository extends TokenRepository {

  /**
   * 토큰 해시가 블랙리스트에 있는지 확인.
   *
   * @param tokenHash 확인할 토큰의 해시
   * @return 토큰이 블랙리스트에 있으면 true, 그렇지 않으면 false
   */
  boolean isTokenBlacklisted(String tokenHash);

  /**
   * 사용자의 액세스 토큰을 검색.
   *
   * @param userId 사용자 ID
   * @return 액세스 토큰, 존재하지 않으면 null 반환
   */
  String getAccessToken(String userId);

  /**
   * 사용자의 리프레시 토큰을 검색.
   *
   * @param userId 사용자 ID
   * @return 리프레시 토큰, 존재하지 않으면 null 반환
   */
  String getRefreshToken(String userId);
}