package com.logistic.user.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.logistic.user.domain.exception.UserServiceErrorCode;
import com.logistic.user.domain.exception.UserServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ValueObjectsTest {

  @Nested
  @DisplayName("UserId VO 테스트")
  class UserIdTest {
    @Test
    @DisplayName("유효한 사용자 ID - 생성 성공")
    void of_withValidUserId_shouldCreateObject() {
      // Given
      String validId = "testuser";

      // When
      UserId userId = UserId.of(validId);

      // Then
      assertThat(userId).isNotNull();
      assertThat(userId.value()).isEqualTo(validId);
    }

    @Test
    @DisplayName("null 사용자 ID - 예외 발생")
    void of_withNullUserId_shouldThrowException() {
      // When & Then
      assertThatThrownBy(() -> UserId.of(null))
          .isInstanceOf(UserServiceException.class)
          .satisfies(ex -> assertThat(((UserServiceException) ex).getError()).isEqualTo(
              UserServiceErrorCode.NOT_NULL_USER_ID));
    }

    @Test
    @DisplayName("빈 사용자 ID - 예외 발생")
    void of_withEmptyUserId_shouldThrowException() {
      // When & Then
      assertThatThrownBy(() -> UserId.of(""))
          .isInstanceOf(UserServiceException.class)
          .satisfies(ex -> assertThat(((UserServiceException) ex).getError()).isEqualTo(
              UserServiceErrorCode.NOT_NULL_USER_ID));
    }

    @Test
    @DisplayName("공백만 있는 사용자 ID - 예외 발생")
    void of_withBlankUserId_shouldThrowException() {
      // When & Then
      assertThatThrownBy(() -> UserId.of("   "))
          .isInstanceOf(UserServiceException.class)
          .satisfies(ex -> assertThat(((UserServiceException) ex).getError()).isEqualTo(
              UserServiceErrorCode.NOT_NULL_USER_ID));
    }
  }

  @Nested
  @DisplayName("Name VO 테스트")
  class NameTest {
    @Test
    @DisplayName("유효한 이름 - 생성 성공")
    void of_withValidName_shouldCreateObject() {
      // Given
      String validName = "홍길동";

      // When
      Name name = Name.of(validName);

      // Then
      assertThat(name).isNotNull();
      assertThat(name.value()).isEqualTo(validName);
    }

    @Test
    @DisplayName("null 이름 - 예외 발생")
    void of_withNullName_shouldThrowException() {
      // When & Then
      assertThatThrownBy(() -> Name.of(null))
          .isInstanceOf(UserServiceException.class)
          .satisfies(ex -> assertThat(((UserServiceException) ex).getError()).isEqualTo(
              UserServiceErrorCode.NOT_NULL_USER_NAME));
    }

    @ParameterizedTest
    @ValueSource(strings = {"홍", "홍길동이름여섯글자"})
    @DisplayName("길이 제한 위반 이름 - 예외 발생")
    void of_withInvalidLengthName_shouldThrowException(String invalidName) {
      // When & Then
      assertThatThrownBy(() -> Name.of(invalidName))
          .isInstanceOf(UserServiceException.class)
          .satisfies(ex -> assertThat(((UserServiceException) ex).getError()).isEqualTo(
              UserServiceErrorCode.INVALID_USER_NAME));
    }
  }

  @Nested
  @DisplayName("Email VO 테스트")
  class EmailTest {
    @Test
    @DisplayName("유효한 이메일 - 생성 성공")
    void of_withValidEmail_shouldCreateObject() {
      // Given
      String validEmail = "test@example.com";

      // When
      Email email = Email.of(validEmail);

      // Then
      assertThat(email).isNotNull();
      assertThat(email.value()).isEqualTo(validEmail);
    }

    @Test
    @DisplayName("null 이메일 - 예외 발생")
    void of_withNullEmail_shouldThrowException() {
      // When & Then
      assertThatThrownBy(() -> Email.of(null))
          .isInstanceOf(UserServiceException.class)
          .satisfies(ex -> assertThat(((UserServiceException) ex).getError()).isEqualTo(
              UserServiceErrorCode.NOT_NULL_USER_EMAIL));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "testexample.com",     // @ 없음
        "test@",               // 도메인 없음
        "@example.com",        // 로컬 파트 없음
        "test@example",        // 최상위 도메인 없음
        "test@.com",           // 서브도메인 없음
        "test..test@example.com" // 연속된 점
    })
    @DisplayName("유효하지 않은 이메일 형식 - 예외 발생")
    void of_withInvalidEmailFormat_shouldThrowException(String invalidEmail) {
      // When & Then
      assertThatThrownBy(() -> Email.of(invalidEmail))
          .isInstanceOf(UserServiceException.class)
          .satisfies(ex -> assertThat(((UserServiceException) ex).getError()).isEqualTo(
              UserServiceErrorCode.INVALID_USER_EMAIL));
    }
  }

  @Nested
  @DisplayName("Password VO 테스트")
  class PasswordTest {
    @Test
    @DisplayName("유효한 평문 비밀번호 - 생성 성공")
    void ofPlainPassword_withValidPassword_shouldCreateObject() {
      // Given
      String validPassword = "Password123!";

      // When
      Password password = Password.ofPlainPassword(validPassword);

      // Then
      assertThat(password).isNotNull();
      assertThat(password.value()).isEqualTo(validPassword);
    }

    @Test
    @DisplayName("해시된 비밀번호 - 생성 성공")
    void ofHashedPassword_withHashedPassword_shouldCreateObject() {
      // Given
      String hashedPassword = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";

      // When
      Password password = Password.ofHashedPassword(hashedPassword);

      // Then
      assertThat(password).isNotNull();
      assertThat(password.value()).isEqualTo(hashedPassword);
    }

    @Test
    @DisplayName("null 비밀번호 - 예외 발생")
    void ofPlainPassword_withNullPassword_shouldThrowException() {
      // When & Then
      assertThatThrownBy(() -> Password.ofPlainPassword(null))
          .isInstanceOf(UserServiceException.class)
          .satisfies(ex -> assertThat(((UserServiceException) ex).getError()).isEqualTo(
              UserServiceErrorCode.NOT_NULL_USER_PASSWORD));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "pass1!",              // 너무 짧음
        "password123!",        // 대문자 없음
        "PASSWORD123!",        // 소문자 없음
        "Password123",         // 특수문자 없음
        "너무너무긴패스워드Password123!" // 너무 김
    })
    @DisplayName("유효하지 않은 비밀번호 형식 - 예외 발생")
    void ofPlainPassword_withInvalidPasswordFormat_shouldThrowException(String invalidPassword) {
      // When & Then
      assertThatThrownBy(() -> Password.ofPlainPassword(invalidPassword))
          .isInstanceOf(UserServiceException.class)
          .satisfies(ex -> assertThat(((UserServiceException) ex).getError()).isEqualTo(
              UserServiceErrorCode.INVALID_USER_PASSWORD));
    }
  }

  @Nested
  @DisplayName("UserRole VO 테스트")
  class UserRoleTest {
    @Test
    @DisplayName("유효한 역할 정보 - 생성 성공")
    void of_withValidRole_shouldCreateObject() {
      // Given
      Long roleId = 1L;
      String roleName = "MASTER_ADMIN";

      // When
      UserRole role = UserRole.of(roleId, roleName);

      // Then
      assertThat(role).isNotNull();
      assertThat(role.roleId()).isEqualTo(roleId);
      assertThat(role.name()).isEqualTo(roleName);
    }

    @Test
    @DisplayName("null 역할 ID - 예외 발생")
    void of_withNullRoleId_shouldThrowException() {
      // When & Then
      assertThatThrownBy(() -> UserRole.of(null, "MASTER_ADMIN"))
          .isInstanceOf(UserServiceException.class)
          .satisfies(ex -> assertThat(((UserServiceException) ex).getError()).isEqualTo(
              UserServiceErrorCode.NOT_NULL_ROLE_ID));
    }

    @Test
    @DisplayName("null 역할 이름 - 예외 발생")
    void of_withNullRoleName_shouldThrowException() {
      // When & Then
      assertThatThrownBy(() -> UserRole.of(1L, null))
          .isInstanceOf(UserServiceException.class)
          .satisfies(ex -> assertThat(((UserServiceException) ex).getError()).isEqualTo(
              UserServiceErrorCode.NOT_NULL_ROLE_NAME));
    }

    @Test
    @DisplayName("빈 역할 이름 - 예외 발생")
    void of_withEmptyRoleName_shouldThrowException() {
      // When & Then
      assertThatThrownBy(() -> UserRole.of(1L, ""))
          .isInstanceOf(UserServiceException.class)
          .satisfies(ex -> assertThat(((UserServiceException) ex).getError()).isEqualTo(
              UserServiceErrorCode.NOT_NULL_ROLE_NAME));
    }
  }
}