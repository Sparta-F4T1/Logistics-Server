package com.logistic.user.adapter.in.web;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.passport.annotation.WithPassport;
import com.logistic.common.passport.model.Passport;
import com.logistic.common.response.ApiResponse;
import com.logistic.user.adapter.in.web.mapper.UserWebMapper;
import com.logistic.user.adapter.in.web.request.RegisterUserRequest;
import com.logistic.user.adapter.in.web.response.FindUserResponse;
import com.logistic.user.application.port.in.UserCommandUseCase;
import com.logistic.user.application.port.in.UserQueryUseCase;
import com.logistic.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserWebAdapter {
  private final UserCommandUseCase userCommandUseCase;
  private final UserQueryUseCase userQueryUseCase;
  private final UserWebMapper userWebMapper;

  @PostMapping
  public ResponseEntity<ApiResponse<FindUserResponse>> registerUser(
      @WithPassport Passport passport,
      @Valid @RequestBody final RegisterUserRequest request) {
    final User user = userCommandUseCase.registerUser(userWebMapper.toCreateCommand(request, passport));
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(userWebMapper.toUserResponse(user)));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<ApiResponse<FindUserResponse>> findUser(
      @WithPassport Passport passport,
      @PathVariable("userId") final String userId) {
    final User user = userQueryUseCase.findUser(userWebMapper.toFindQuery(userId, passport));
    return ResponseEntity.ok()
        .body(ApiResponse.success(userWebMapper.toUserResponse(user)));
  }
}
