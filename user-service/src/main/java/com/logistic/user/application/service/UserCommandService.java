package com.logistic.user.application.service;

import com.logistic.common.passport.model.RoleType;
import com.logistic.user.application.port.in.UserCommandUseCase;
import com.logistic.user.application.port.in.command.DeleteUserCommand;
import com.logistic.user.application.port.in.command.RegisterUserCommand;
import com.logistic.user.application.port.in.command.UpdateUserCommand;
import com.logistic.user.application.port.in.command.UpdateUserStatusCommand;
import com.logistic.user.application.port.out.persistence.UserPersistencePort;
import com.logistic.user.domain.User;
import com.logistic.user.domain.command.UserForUpdate;
import com.logistic.user.domain.exception.UserServiceErrorCode;
import com.logistic.user.domain.exception.UserServiceException;
import com.logistic.user.domain.vo.UserStatus;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandService implements UserCommandUseCase {
  // TODO: 세밀한 검증(인가) 검토하기
  private final UserPersistencePort persistencePort;
  private final PasswordEncoder passwordEncoder;

  @Override
  public User registerUser(final RegisterUserCommand command) {
    validatePasswordMatch(command.password(), command.confirmedPassword());

    String roleType = command.roleName();
    validateRoleType(roleType);

    checkDuplicateUserId(command.userId());

    checkDuplicateSlackAccount(command.slackAccount());

    User user = User.create(
        command.userId(),
        command.name(),
        command.password(),
        command.slackAccount(),
        command.roleId(),
        roleType
    );

    String hashedPassword = passwordEncoder.encode(user.getPassword().value());
    user.updateHashedPassword(hashedPassword);

    log.info("사용자 생성: userId={}", command.userId());
    return persistencePort.save(user);
  }

  @Override
  public User updateUser(final UpdateUserCommand command) {
    validatePasswordMatchIfPresent(command.password(), command.confirmedPassword());

    User targetUser = persistencePort.findByUserId(command.targetUserId());

    validateDuplicateSlackAccountIfUpdated(command.slackAccount(), targetUser.getSlackAccount().value());

    UserForUpdate forUpdate = UserForUpdate.of(command.password(), command.slackAccount());
    targetUser.update(forUpdate);

    if (command.password() != null && !command.password().isBlank()) {
      String hashedPassword = passwordEncoder.encode(command.password());
      targetUser.updateHashedPassword(hashedPassword);
    }

    log.info("사용자 정보 업데이트: userId={}", command.targetUserId());
    return persistencePort.update(targetUser);
  }

  @Override
  public UserStatus updateUserStatus(UpdateUserStatusCommand command) {
    User user = persistencePort.findByUserId(command.targetUserId());

    UserStatus targetStatus = UserStatus.fromString(command.status());
    user.changeStatus(targetStatus);

    log.info("사용자 상태 변경: userId={}, newStatus={}", command.targetUserId(), targetStatus);
    return persistencePort.update(user).getStatus();
  }

  @Override
  public void deleteUser(DeleteUserCommand command) {
    User user = persistencePort.findByUserId(command.targetUserId());

    validateUserDeletable(user);

    user.deactivate();

    String deletedBy = (command.currentUserId() != null && !command.currentUserId().isBlank())
        ? command.currentUserId()
        : "SYSTEM";

    persistencePort.delete(user, deletedBy);

    log.info("계정 삭제 완료: userId={}, deletedBy={}", command.targetUserId(), deletedBy);
  }

  private void validatePasswordMatch(String password, String confirmedPassword) {
    if (password == null || confirmedPassword == null || !password.equals(confirmedPassword)) {
      throw UserServiceException.user(UserServiceErrorCode.PASSWORD_NOT_MATCH);
    }
  }

  private void validateRoleType(String roleName) {
    try {
      RoleType.valueOf(roleName);
    } catch (IllegalArgumentException e) {
      throw UserServiceException.user(
          UserServiceErrorCode.INVALID_ROLE_TYPE,
          "유효하지 않은 역할 타입입니다. 가능한 타입: " + Arrays.toString(RoleType.values())
      );
    }
  }

  private void checkDuplicateUserId(String userId) {
    if (persistencePort.existsByUserId(userId)) {
      throw UserServiceException.user(UserServiceErrorCode.DUPLICATE_USER_ID);
    }
  }

  private void checkDuplicateSlackAccount(String slackAccount) {
    if (slackAccount != null && !slackAccount.isBlank() &&
        persistencePort.existsBySlackAccount(slackAccount)) {
      throw UserServiceException.user(UserServiceErrorCode.DUPLICATE_SLACK_ACCOUNT);
    }
  }

  private void validatePasswordMatchIfPresent(String password, String confirmedPassword) {
    if ((password == null || password.isBlank()) && (confirmedPassword == null || confirmedPassword.isBlank())) {
      return;
    }

    if (password == null || confirmedPassword == null || !password.equals(confirmedPassword)) {
      throw UserServiceException.user(UserServiceErrorCode.PASSWORD_NOT_MATCH);
    }
  }

  private void validateDuplicateSlackAccountIfUpdated(String newSlackAccount, String existingSlackAccount) {
    if (newSlackAccount == null || newSlackAccount.isBlank() ||
        newSlackAccount.equals(existingSlackAccount)) {
      return;
    }

    if (persistencePort.existsBySlackAccount(newSlackAccount)) {
      throw UserServiceException.user(UserServiceErrorCode.DUPLICATE_SLACK_ACCOUNT);
    }
  }

  private void validateUserDeletable(User user) {
    if (isProtectedRole(user.getRole().name())) {
      throw UserServiceException.user(
          UserServiceErrorCode.PROTECTED_USER_ROLE,
          "보호된 역할을 가진 사용자는 삭제할 수 없습니다."
      );
    }

    if (!user.isUsable()) {
      throw UserServiceException.user(
          UserServiceErrorCode.USER_ALREADY_INACTIVE,
          "이미 비활성화된 사용자입니다."
      );
    }
  }

  private boolean isProtectedRole(String role) {
    try {
      RoleType roleType = RoleType.valueOf(role);
      return roleType == RoleType.MASTER_ADMIN;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}