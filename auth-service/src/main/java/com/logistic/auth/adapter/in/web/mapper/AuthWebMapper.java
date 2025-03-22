package com.logistic.auth.adapter.in.web.mapper;

import com.logistic.auth.adapter.in.web.request.LoginRequest;
import com.logistic.auth.adapter.in.web.request.LogoutRequest;
import com.logistic.auth.adapter.in.web.request.RefreshTokenRequest;
import com.logistic.auth.adapter.in.web.response.LoginResponse;
import com.logistic.auth.adapter.in.web.response.RefreshTokenResponse;
import com.logistic.auth.application.port.in.command.LoginCommand;
import com.logistic.auth.application.port.in.command.LogoutCommand;
import com.logistic.auth.application.port.in.command.RefreshCommand;
import com.logistic.auth.domain.TokenPair;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthWebMapper {
  LoginCommand toLoginCommand(LoginRequest request);

  @Mapping(target = "accessToken", source = "accessTokenCredential.tokenValue")
  @Mapping(target = "refreshToken", source = "refreshTokenCredential.tokenValue")
  LoginResponse toLoginResponse(TokenPair tokenPair);

  @Mapping(target = "refreshToken", source = "refreshToken")
  RefreshCommand toRefreshCommand(RefreshTokenRequest request);

  @Mapping(target = "accessToken", source = "accessTokenCredential.tokenValue")
  @Mapping(target = "refreshToken", source = "refreshTokenCredential.tokenValue")
  RefreshTokenResponse toRefreshResponse(TokenPair tokenPair);

  LogoutCommand toLogoutCommand(LogoutRequest request);
}
