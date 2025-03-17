package com.logistic.authcore.service;

public interface TokenService {

  String INVALID_REFRESH_TOKEN_MESSAGE = "리프레시 토큰이 유효하지 않거나 존재하지 않습니다.";
  String INVALID_ACCESS_TOKEN_MESSAGE = "액세스 토큰이 유효하지 않거나 블랙리스트에 있습니다.";
}