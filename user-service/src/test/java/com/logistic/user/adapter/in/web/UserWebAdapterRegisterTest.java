package com.logistic.user.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.logistic.common.response.ApiResponse;
import com.logistic.user.adapter.in.web.mapper.UserWebMapper;
import com.logistic.user.adapter.in.web.request.RegisterUserRequest;
import com.logistic.user.adapter.in.web.response.FindUserResponse;
import com.logistic.user.application.port.in.UserCommandUseCase;
import com.logistic.user.application.port.in.command.RegisterUserCommand;
import com.logistic.user.domain.User;
import com.logistic.user.domain.exception.UserServiceErrorCode;
import com.logistic.user.domain.exception.UserServiceException;
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

  private RegisterUserRequest registerUserRequest(String username, String email, String roleName) {
    return new RegisterUserRequest(
        username, "홍길동", "Password123!", email, 1L, roleName
    );
  }

  private RegisterUserRequest registerUserRequest(String username, String email) {
    return registerUserRequest(
        username, email, "MASTER_ADMIN"
    );
  }

  private RegisterUserCommand registerUserCommand(RegisterUserRequest request) {
    return new RegisterUserCommand(
        request.userId(), request.name(), request.password(), request.slackAccount(), request.roleId(),
        request.roleName()
    );
  }

  @BeforeEach
  void setUp() {
    when(userWebMapper.toCreateCommand(any(RegisterUserRequest.class)))
        .thenAnswer(invocation -> registerUserCommand(invocation.getArgument(0)));
  }

  @Test
  @DisplayName("유효한 사용자 등록 요청 - 성공")
  void registerUser_withValidRequest_shouldReturnCreatedStatus() {
    // Given
    RegisterUserRequest request = registerUserRequest("testuser", "test@example.com");
    User mockUser = mock(User.class);
    ApiResponse<FindUserResponse> expectedResponse = ApiResponse.success(new FindUserResponse(
        "testuser", "홍길동", "test@example.com", 1L, "MASTER_ADMIN", "ACTIVE"
    ));

    when(userCommandUseCase.registerUser(any())).thenReturn(mockUser);
    when(userWebMapper.toUserResponse(mockUser)).thenReturn(expectedResponse.data());

    // When
    ResponseEntity<ApiResponse<FindUserResponse>> response = userWebAdapter.registerUser(request);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody()).isEqualTo(expectedResponse);

    verify(userWebMapper).toCreateCommand(request);
    verify(userCommandUseCase).registerUser(any());
    verify(userWebMapper).toUserResponse(mockUser);
  }

  @Test
  @DisplayName("중복된 사용자 ID - 등록 실패")
  void registerUser_withDuplicateUserId_shouldThrowException() {
    RegisterUserRequest request = registerUserRequest("existinguser", "test@example.com");
    when(userCommandUseCase.registerUser(any()))
        .thenThrow(new UserServiceException(UserServiceErrorCode.DUPLICATE_USER_ID));

    verifyUserRegistrationFailure(request, UserServiceErrorCode.DUPLICATE_USER_ID);
  }

  @Test
  @DisplayName("중복된 슬랙 계정 - 등록 실패")
  void registerUser_withDuplicateSlackAccount_shouldThrowException() {
    RegisterUserRequest request = registerUserRequest("testuser", "existing@example.com");
    when(userCommandUseCase.registerUser(any()))
        .thenThrow(new UserServiceException(UserServiceErrorCode.DUPLICATE_SLACK_ACCOUNT));

    verifyUserRegistrationFailure(request, UserServiceErrorCode.DUPLICATE_SLACK_ACCOUNT);
  }

  private void verifyUserRegistrationFailure(RegisterUserRequest request, UserServiceErrorCode expectedError) {
    assertThatThrownBy(() -> userWebAdapter.registerUser(request))
        .isInstanceOf(UserServiceException.class)
        .satisfies(ex -> assertThat(((UserServiceException) ex).getError()).isEqualTo(expectedError));

    verify(userWebMapper).toCreateCommand(request);
    verify(userCommandUseCase).registerUser(any());
    verify(userWebMapper, never()).toUserResponse(any(User.class));
  }
}