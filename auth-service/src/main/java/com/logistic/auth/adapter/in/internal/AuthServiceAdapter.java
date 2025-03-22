package com.logistic.auth.adapter.in.internal;

import com.logistic.auth.adapter.in.internal.mapper.AuthServiceMapper;
import com.logistic.auth.application.port.in.AuthCommandUseCase;
import com.logistic.auth.application.port.in.AuthQueryUseCase;
import com.logistic.auth.domain.vo.UserId;
import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.request.AuthClientRequest;
import com.logistic.common.internal.response.AuthClientResponse;
import com.logistic.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequestMapping("/internal/v1/auth")
@RequiredArgsConstructor
public class AuthServiceAdapter {
  private final AuthServiceMapper mapper;
  private final AuthQueryUseCase queryUseCase;
  private final AuthCommandUseCase commandUseCase;

  @PostMapping("/verify-token")
  public ResponseEntity<ApiResponse<AuthClientResponse>> validateToken(@RequestBody AuthClientRequest request) {
    UserId userId = queryUseCase.validateToken(mapper.toVerifyTokenQuery(request));
    ApiResponse<AuthClientResponse> response = ApiResponse.success(mapper.toAuthClientSuccessResponse(userId));
    return ResponseEntity.ok().body(response);
  }
}
