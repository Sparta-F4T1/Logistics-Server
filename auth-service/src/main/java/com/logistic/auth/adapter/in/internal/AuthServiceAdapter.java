package com.logistic.auth.adapter.in.internal;

import com.logistic.auth.adapter.in.internal.mapper.AuthServiceMapper;
import com.logistic.auth.application.port.in.AuthenticationQueryUseCase;
import com.logistic.auth.application.port.in.AuthorizationCommandUseCase;
import com.logistic.auth.domain.Passport;
import com.logistic.auth.domain.vo.UserId;
import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.request.AuthClientRequest;
import com.logistic.common.internal.response.AuthClientResponse;
import com.logistic.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@Adapter
@RestController
@RequestMapping("/internal/v1/auth")
@RequiredArgsConstructor
public class AuthServiceAdapter {
  private final AuthServiceMapper mapper;
  private final AuthenticationQueryUseCase authenticationQueryUseCase;
  private final AuthorizationCommandUseCase authorizationCommandUseCase;

  @PostMapping("/verify-token")
  public ResponseEntity<ApiResponse<AuthClientResponse>> validateToken(@RequestBody final AuthClientRequest request) {
    UserId userId = authenticationQueryUseCase.validateToken(mapper.toVerifyTokenQuery(request));
    ApiResponse<AuthClientResponse> response = ApiResponse.success(mapper.toAuthClientSuccessResponse(userId));

    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/access/validate")
  public ResponseEntity<ApiResponse<AuthClientResponse>> validateAccess(@RequestBody final AuthClientRequest request) {
    Passport passport = authorizationCommandUseCase.issuePassport(mapper.toIssuePassportCommand(request));
    ApiResponse<AuthClientResponse> response = ApiResponse.success(mapper.toAuthClientSuccessResponse(passport));

    return ResponseEntity.ok().body(response);
  }
}