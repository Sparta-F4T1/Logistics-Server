package com.logistic.user.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.logistic.user.application.port.in.command.RegisterUserCommand;
import com.logistic.user.application.port.out.persistence.UserPersistencePort;
import com.logistic.user.domain.User;
import com.logistic.user.domain.exception.UserServiceErrorCode;
import com.logistic.user.domain.exception.UserServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserCommandServiceRegisterTest {

  @Mock
  private UserPersistencePort userPersistencePort;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserCommandService userCommandService;

  private RegisterUserCommand createRegisterCommand(String userId, String email, String roleName) {
    return new RegisterUserCommand(
        userId, "홍길동", "Password123!", "Password123!", email, 1L, roleName, "admin", "ADMIN_ROLE"
    );
  }

  private RegisterUserCommand createRegisterCommandWithMismatchedPassword(String userId, String email,
                                                                          String roleName) {
    return new RegisterUserCommand(
        userId, "홍길동", "Password123!", "DifferentPassword!", email, 1L, roleName, "admin", "ADMIN_ROLE"
    );
  }

  private User createUser(RegisterUserCommand command) {
    return User.create(
        command.userId(),
        command.name(),
        "Password!123",
        command.slackAccount(),
        command.roleId(), command.roleName());
  }

  @Test
  @DisplayName("유효한 데이터로 사용자 등록 - 성공")
  void registerUser_withValidData_shouldSucceed() {
    // Given
    RegisterUserCommand command = createRegisterCommand("testuser", "test@example.com", "MASTER_ADMIN");
    User expectedUser = createUser(command);

    when(userPersistencePort.existsByUserId(command.userId())).thenReturn(false);
    when(userPersistencePort.existsBySlackAccount(command.slackAccount())).thenReturn(false);
    when(passwordEncoder.encode(command.password())).thenReturn("hashedPassword");
    when(userPersistencePort.save(any(User.class))).thenReturn(expectedUser);

    // When
    User result = userCommandService.registerUser(command);

    // Then
    assertThat(result)
        .usingRecursiveComparison()
        .isEqualTo(expectedUser);

    verify(userPersistencePort).existsByUserId(command.userId());
    verify(userPersistencePort).existsBySlackAccount(command.slackAccount());
    verify(passwordEncoder).encode(command.password());
    verify(userPersistencePort).save(any(User.class));
  }

  @Test
  @DisplayName("비밀번호와 확인 비밀번호 불일치 - 등록 실패")
  void registerUser_withMismatchedPasswords_shouldThrowException() {
    // Given
    RegisterUserCommand command = createRegisterCommandWithMismatchedPassword(
        "testuser", "test@example.com", "MASTER_ADMIN");

    // When & Then
    assertThatThrownBy(() -> userCommandService.registerUser(command))
        .isInstanceOf(UserServiceException.class)
        .satisfies(
            ex -> assertThat(((UserServiceException) ex).getError()).isEqualTo(
                UserServiceErrorCode.PASSWORD_NOT_MATCH));

    verify(userPersistencePort, never()).existsByUserId(anyString());
    verify(userPersistencePort, never()).existsBySlackAccount(anyString());
    verify(passwordEncoder, never()).encode(anyString());
    verify(userPersistencePort, never()).save(any(User.class));
  }

  @Test
  @DisplayName("중복된 사용자 ID - 등록 실패")
  void registerUser_withDuplicateUserId_shouldThrowException() {
    // Given
    RegisterUserCommand command = createRegisterCommand("existinguser", "test@example.com", "MASTER_ADMIN");

    when(userPersistencePort.existsByUserId(command.userId())).thenReturn(true);

    // When & Then
    assertThatThrownBy(() -> userCommandService.registerUser(command))
        .isInstanceOf(UserServiceException.class)
        .satisfies(
            ex -> assertThat(((UserServiceException) ex).getError()).isEqualTo(
                UserServiceErrorCode.DUPLICATE_USER_ID));

    verify(userPersistencePort).existsByUserId(command.userId());
    verify(userPersistencePort, never()).existsBySlackAccount(anyString());
    verify(passwordEncoder, never()).encode(anyString());
    verify(userPersistencePort, never()).save(any(User.class));
  }

  @Test
  @DisplayName("중복된 슬랙 계정 - 등록 실패")
  void registerUser_withDuplicateSlackAccount_shouldThrowException() {
    // Given
    RegisterUserCommand command = createRegisterCommand("testuser", "existing@example.com", "MASTER_ADMIN");

    when(userPersistencePort.existsByUserId(command.userId())).thenReturn(false);
    when(userPersistencePort.existsBySlackAccount(command.slackAccount())).thenReturn(true);

    // When & Then
    assertThatThrownBy(() -> userCommandService.registerUser(command))
        .isInstanceOf(UserServiceException.class)
        .satisfies(
            ex -> assertThat(((UserServiceException) ex).getError()).isEqualTo(
                UserServiceErrorCode.DUPLICATE_SLACK_ACCOUNT));

    verify(userPersistencePort).existsByUserId(command.userId());
    verify(userPersistencePort).existsBySlackAccount(command.slackAccount());
    verify(passwordEncoder, never()).encode(anyString());
    verify(userPersistencePort, never()).save(any(User.class));
  }

  @Test
  @DisplayName("유효하지 않은 역할 타입 - 등록 실패")
  void registerUser_withInvalidRoleType_shouldThrowException() {
    String invalidRoleType = "INVALID_ROLE";
    RegisterUserCommand command = createRegisterCommand("testuser", "test@example.com", invalidRoleType);

    // When & Then
    assertThatThrownBy(() -> userCommandService.registerUser(command))
        .isInstanceOf(UserServiceException.class);
  }

  @Test
  @DisplayName("null 입력값 - 등록 실패")
  void registerUser_withNullCommand_shouldThrowException() {
    // When & Then
    assertThatThrownBy(() -> userCommandService.registerUser(null))
        .isInstanceOf(NullPointerException.class);

    verify(userPersistencePort, never()).existsByUserId(anyString());
    verify(userPersistencePort, never()).existsBySlackAccount(anyString());
    verify(passwordEncoder, never()).encode(anyString());
    verify(userPersistencePort, never()).save(any(User.class));
  }
}