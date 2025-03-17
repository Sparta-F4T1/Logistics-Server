package com.logistic.authcore.service;

import io.jsonwebtoken.Claims;

public interface TokenVerificationService extends TokenService {

  /**
   * 토큰을 검증하고 클레임을 반환합니다.
   *
   * @param token 검증할 JWT 토큰
   * @return 토큰에 포함된 클레임
   * @throws InvalidTokenException 토큰이 유효하지 않거나 블랙리스트에 있는 경우
   */
  Claims validateToken(String token);

  /**
   * 액세스 토큰을 검증하고 클레임을 반환합니다.
   *
   * @param accessToken 검증할 액세스 토큰
   * @return 토큰에 포함된 클레임
   * @throws InvalidTokenException 토큰이 유효하지 않거나 블랙리스트에 있는 경우
   */
  Claims validateAccessToken(String accessToken);

  /**
   * 리프레시 토큰을 검증하고 클레임을 반환합니다.
   *
   * @param refreshToken 검증할 리프레시 토큰
   * @return 토큰에 포함된 클레임
   * @throws InvalidTokenException 토큰이 유효하지 않거나 저장소에 없는 경우
   */
  Claims validateRefreshToken(String refreshToken);

  /**
   * 토큰이 블랙리스트에 있는지 확인합니다.
   *
   * @param token 확인할 토큰
   * @return 블랙리스트에 있으면 true, 아니면 false
   */
  boolean isTokenBlacklisted(String token);

  /**
   * 토큰에서 사용자 ID를 추출합니다.
   *
   * @param token 사용자 ID를 추출할 토큰
   * @return 사용자 ID
   * @throws InvalidTokenException 토큰이 유효하지 않은 경우
   */
  String getUserIdFromToken(String token);
}