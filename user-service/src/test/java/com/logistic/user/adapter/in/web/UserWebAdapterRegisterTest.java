package com.logistic.user.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.logistic.common.passport.model.Passport;
import com.logistic.common.passport.model.SessionInfo;
import com.logistic.common.passport.model.UserInfo;
import com.logistic.common.response.ApiResponse;
import com.logistic.user.adapter.in.web.mapper.UserWebMapper;
import com.logistic.user.adapter.in.web.request.RegisterUserRequest;
import com.logistic.user.adapter.in.web.response.FindUserResponse;
import com.logistic.user.application.port.in.UserCommandUseCase;
import com.logistic.user.application.port.in.command.RegisterUserCommand;
import com.logistic.user.domain.User;
import com.logistic.user.domain.exception.UserServiceErrorCode;
import com.logistic.user.domain.exception.UserServiceException;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UserWebAdapterRegisterTest {

  @Mock
  private UserCommandUseCase userCommandUseCase;

  @Mock
  private UserWebMapper userWebMapper;

  @InjectMocks
  private UserWebAdapter userWebAdapter;

  private Passport createTestPassport() {
    UserInfo userInfo = new UserInfo("admin", "ADMIN", null);
    SessionInfo sessionInfo = new SessionInfo("test-session", Instant.now(), Instant.now().plusSeconds(3600));
    return new Passport(userInfo, sessionInfo);
  }

  private RegisterUserRequest registerUserRequest(String username, String email, String roleName) {
    return new RegisterUserRequest(
        username, "홍길동", "Password123!", "Password123!", email, 1L, roleName
    );
  }

  private RegisterUserRequest registerUserRequest(String username, String email) {
    return registerUserRequest(
        username, email, "MASTER_ADMIN"
    );
  }

  private RegisterUserCommand registerUserCommand(RegisterUserRequest request, Passport passport) {
    return new RegisterUserCommand(
        request.userId(),
        request.name(),
        request.password(),
        request.confirmedPassword(),
        request.slackAccount(),
        request.roleId(),
        request.roleName(),
        passport.getUserInfo().getUserId(),
        passport.getUserInfo().getRole()
    );
  }

  @BeforeEach
  void setUp() {
    when(userWebMapper.toCreateCommand(any(RegisterUserRequest.class), any(Passport.class)))
        .thenAnswer(invocation -> registerUserCommand(
            invocation.getArgument(0),
            invocation.getArgument(1)
        ));
  }

  @Test
  @DisplayName("유효한 사용자 등록 요청 - 성공")
  void registerUser_withValidRequest_shouldReturnCreatedStatus() {
    // Given
    RegisterUserRequest request = registerUserRequest("testuser", "test@example.com");
    Passport passport = createTestPassport();
    User mockUser = mock(User.class);
    ApiResponse<FindUserResponse> expectedResponse = ApiResponse.success(new FindUserResponse(
        "testuser", "홍길동", "test@example.com", 1L, "MASTER_ADMIN", "ACTIVE"
    ));

    when(userCommandUseCase.registerUser(any())).thenReturn(mockUser);
    when(userWebMapper.toUserResponse(mockUser)).thenReturn(expectedResponse.data());

    // When
    ResponseEntity<ApiResponse<FindUserResponse>> response = userWebAdapter.registerUser(passport, request);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody()).isEqualTo(expectedResponse);

    verify(userWebMapper).toCreateCommand(request, passport);
    verify(userCommandUseCase).registerUser(any());
    verify(userWebMapper).toUserResponse(mockUser);
  }

  @Test
  @DisplayName("중복된 사용자 ID - 등록 실패")
  void registerUser_withDuplicateUserId_shouldThrowException() {
    RegisterUserRequest request = registerUserRequest("existinguser", "test@example.com");
    Passport passport = createTestPassport();

    when(userCommandUseCase.registerUser(any()))
        .thenThrow(new UserServiceException(UserServiceErrorCode.DUPLICATE_USER_ID));

    verifyUserRegistrationFailure(request, passport, UserServiceErrorCode.DUPLICATE_USER_ID);
  }

  @Test
  @DisplayName("중복된 슬랙 계정 - 등록 실패")
  void registerUser_withDuplicateSlackAccount_shouldThrowException() {
    RegisterUserRequest request = registerUserRequest("testuser", "existing@example.com");
    Passport passport = createTestPassport();

    when(userCommandUseCase.registerUser(any()))
        .thenThrow(new UserServiceException(UserServiceErrorCode.DUPLICATE_SLACK_ACCOUNT));

    verifyUserRegistrationFailure(request, passport, UserServiceErrorCode.DUPLICATE_SLACK_ACCOUNT);
  }

  @Test
  @DisplayName("비밀번호와 확인 비밀번호 불일치 - 등록 실패")
  void registerUser_withMismatchedPasswords_shouldThrowException() {
    // 비밀번호 불일치 요청 객체 직접 생성
    RegisterUserRequest request = new RegisterUserRequest(
        "testuser", "홍길동", "Password123!", "DifferentPassword!", "test@example.com", 1L, "MASTER_ADMIN"
    );

    Passport passport = createTestPassport();

    when(userCommandUseCase.registerUser(any()))
        .thenThrow(new UserServiceException(UserServiceErrorCode.PASSWORD_NOT_MATCH));

    verifyUserRegistrationFailure(request, passport, UserServiceErrorCode.PASSWORD_NOT_MATCH);
  }

  private void verifyUserRegistrationFailure(RegisterUserRequest request, Passport passport,
                                             UserServiceErrorCode expectedError) {
    assertThatThrownBy(() -> userWebAdapter.registerUser(passport, request))
        .isInstanceOf(UserServiceException.class)
        .satisfies(ex -> assertThat(((UserServiceException) ex).getError()).isEqualTo(expectedError));

    verify(userWebMapper).toCreateCommand(request, passport);
    verify(userCommandUseCase).registerUser(any());
    verify(userWebMapper, never()).toUserResponse(any(User.class));
  }
}