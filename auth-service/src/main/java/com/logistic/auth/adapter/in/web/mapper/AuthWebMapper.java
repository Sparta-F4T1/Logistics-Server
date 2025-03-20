package com.logistic.auth.adapter.in.web.mapper;

import com.logistic.auth.adapter.in.web.request.LoginRequest;
import com.logistic.auth.adapter.in.web.response.LoginResponse;
import com.logistic.auth.application.port.in.command.LoginCommand;
import com.logistic.auth.domain.TokenPair;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthWebMapper {
  LoginCommand toLoginCommand(LoginRequest request);

  @Mapping(target = "accessToken", source = "accessTokenCredential.tokenValue")
  @Mapping(target = "refreshToken", source = "refreshTokenCredential.tokenValue")
  LoginResponse toLoginResponse(TokenPair tokenPair);
}
