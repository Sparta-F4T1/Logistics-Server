package com.logistic.authcore.service;

import java.time.Duration;
import java.util.List;

public interface AuthTokenService extends TokenService {

  /**
   * 액세스 토큰을 생성.
   *
   * @param userId 사용자 ID
   * @return 새로 생성된 액세스 토큰
   */
  String generateAccessToken(String userId);

  /**
   * 리프레시 토큰을 생성.
   *
   * @param userId 사용자 ID
   * @return 새로 생성된 리프레시 토큰
   */
  String generateRefreshToken(String userId);

  /**
   * 로그인 시 토큰 쌍을 생성. (액세스 토큰과 리프레시 토큰)
   *
   * @param userId 사용자 ID
   * @return 토큰 쌍 객체 (액세스 토큰과 리프레시 토큰 포함)
   */
  List<String> generateTokenPair(String userId);

  /**
   * 리프레시 토큰을 사용하여 새 액세스 토큰을 발급.
   *
   * @param refreshToken 리프레시 토큰
   * @return 새로 발급된 액세스 토큰
   * @throws InvalidTokenException 리프레시 토큰이 유효하지 않은 경우
   */
  String refreshAccessToken(String refreshToken);

  /**
   * 리프레시 토큰을 사용하여 새 토큰 쌍(액세스 토큰과 리프레시 토큰)을 발급.
   *
   * @param refreshToken 기존 리프레시 토큰
   * @return 새로 발급된 토큰 쌍
   * @throws InvalidTokenException 리프레시 토큰이 유효하지 않은 경우
   */
  List<String> refreshTokenPair(String refreshToken);

  /**
   * 토큰을 취소. (로그아웃 시 사용)
   *
   * @param accessToken  취소할 액세스 토큰
   * @param refreshToken 취소할 리프레시 토큰 (null일 수 있음)
   */
  void revokeTokens(String accessToken, String refreshToken);

  /**
   * 액세스 토큰을 블랙리스트에 추가.
   *
   * @param token 블랙리스트에 추가할 토큰
   * @return 추가 성공 시 true, 실패 시 false
   */
  boolean blacklistToken(String token);

  /**
   * 토큰을 지정된 기간 동안 블랙리스트에 추가.
   *
   * @param token    블랙리스트에 추가할 토큰
   * @param duration 블랙리스트에 유지할 기간
   * @return 추가 성공 시 true, 실패 시 false
   */
  boolean blacklistToken(String token, Duration duration);

  /**
   * 액세스 토큰을 저장.
   *
   * @param userId      사용자 ID
   * @param accessToken 저장할 액세스 토큰
   */
  void storeAccessToken(String userId, String accessToken);

  /**
   * 액세스 토큰을 지정된 기간 동안 저장.
   *
   * @param userId      사용자 ID
   * @param accessToken 저장할 액세스 토큰
   * @param duration    저장 기간
   */
  void storeAccessToken(String userId, String accessToken, Duration duration);

  /**
   * 리프레시 토큰을 저장.
   *
   * @param userId       사용자 ID
   * @param refreshToken 저장할 리프레시 토큰
   */
  void storeRefreshToken(String userId, String refreshToken);

  /**
   * 리프레시 토큰을 지정된 기간 동안 저장.
   *
   * @param userId       사용자 ID
   * @param refreshToken 저장할 리프레시 토큰
   * @param duration     저장 기간
   */
  void storeRefreshToken(String userId, String refreshToken, Duration duration);

  /**
   * 토큰 쌍을 저장.
   *
   * @param userId       사용자 ID
   * @param accessToken  저장할 액세스 토큰
   * @param refreshToken 저장할 리프레시 토큰
   */
  void storeTokenPair(String userId, String accessToken, String refreshToken);

  /**
   * 토큰 쌍을 지정된 기간 동안 저장.
   *
   * @param userId       사용자 ID
   * @param accessToken  저장할 액세스 토큰
   * @param refreshToken 저장할 리프레시 토큰
   * @param duration     저장 기간
   */
  void storeTokenPair(String userId, String accessToken, String refreshToken, Duration duration);
}