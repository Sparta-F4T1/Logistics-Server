package com.logistic.auth.adapter.in.web;


import com.logistic.auth.adapter.in.web.mapper.AuthWebMapper;
import com.logistic.auth.adapter.in.web.request.RefreshTokenRequest;
import com.logistic.auth.adapter.in.web.response.LoginResponse;
import com.logistic.auth.adapter.in.web.response.RefreshTokenResponse;
import com.logistic.auth.application.port.in.AuthCommandUseCase;
import com.logistic.auth.application.port.in.command.LoginCommand;
import com.logistic.auth.application.port.in.command.RefreshCommand;
import com.logistic.auth.domain.TokenPair;
import com.logistic.common.annotation.Adapter;
import com.logistic.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthWebAdapter {

  private final AuthCommandUseCase commandUseCase;
  private final AuthWebMapper mapper;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<LoginResponse>> login(
      @Valid @RequestBody com.logistic.auth.adapter.in.web.request.LoginRequest request) {
    LoginCommand command = mapper.toLoginCommand(request);
    TokenPair tokenPair = commandUseCase.login(command);
    ApiResponse<LoginResponse> response = ApiResponse.success(mapper.toLoginResponse(tokenPair));

    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/refresh")
  public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshToken(
      @Valid @RequestBody RefreshTokenRequest request) {
    RefreshCommand command = mapper.toRefreshCommand(request);
    TokenPair tokenPair = commandUseCase.refresh(command);
    ApiResponse<RefreshTokenResponse> response = ApiResponse.success(mapper.toRefreshResponse(tokenPair));

    return ResponseEntity.ok().body(response);
  }
}