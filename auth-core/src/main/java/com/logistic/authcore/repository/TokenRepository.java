package com.logistic.authcore.repository;

public interface TokenRepository {

  /**
   * 키 생성 시 사용하는 공통 상수
   */
  String BLACKLIST_PREFIX = "blacklist:";
  String REFRESH_TOKEN_PREFIX = "refresh:";
  String ACCESS_TOKEN_PREFIX = "access:";

  /**
   * 토큰 해시 값을 생성.
   *
   * @param token 해시할 토큰
   * @return 해시된 토큰 값
   */
  String hashToken(String token);
}