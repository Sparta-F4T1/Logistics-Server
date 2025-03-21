package com.logistic.user.application.service;

import com.logistic.common.passport.model.RoleType;
import com.logistic.user.application.port.in.UserCommandUseCase;
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
import java.util.Objects;
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
  // TODO: 추가 권한 체크
  private final UserPersistencePort persistencePort;
  private final PasswordEncoder passwordEncoder;

  @Override
  public User registerUser(final RegisterUserCommand command) {
    String password = command.password();
    validatePasswordMatch(password, command.confirmedPassword());

    String roleType = command.roleName();
    validateRoleType(roleType);

    User user = User.create(command.userId(), command.name(), password, command.slackAccount(),
        command.roleId(), roleType);

    checkDuplicateUserId(command.userId());
    checkDuplicateSlackAccount(command.slackAccount());

    String hashedPassword = passwordEncoder.encode(user.getPassword().value());
    user.updateHashedPassword(hashedPassword);

    return persistencePort.save(user);
  }

  @Override
  public User updateUser(final UpdateUserCommand command) {
    String password = command.password();
    validatePasswordMatchIfPresent(password, command.confirmedPassword());

    User targetUser = persistencePort.findByUserId(command.targetUserId());
    String slackAccount = command.slackAccount();
    validateDuplicateSlackAccountIfPresent(slackAccount, targetUser.getSlackAccount().value());

    UserForUpdate forUpdate = UserForUpdate.of(command.password(), slackAccount);
    targetUser.update(forUpdate);

    if (password != null) {
      password = passwordEncoder.encode(password);
    }
    targetUser.updateHashedPassword(password);

    return persistencePort.update(targetUser);
  }

  @Override
  public UserStatus updateUserStatus(UpdateUserStatusCommand command) {
    User user = persistencePort.findByUserId(command.targetUserId());

    UserStatus targetStatus = UserStatus.fromString(command.status());
    user.changeStatus(targetStatus);

    return persistencePort.update(user).getStatus();
  }

  private void validatePasswordMatch(String password, String confirmedPassword) {
    if (!password.equals(confirmedPassword)) {
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
    if (!Objects.equals(password, confirmedPassword)) {
      throw UserServiceException.user(UserServiceErrorCode.PASSWORD_NOT_MATCH);
    }
  }

  private void validateDuplicateSlackAccountIfPresent(String slackAccount, String existingSlackAccount) {
    if (Objects.equals(slackAccount, existingSlackAccount)) {
      throw UserServiceException.user(UserServiceErrorCode.DUPLICATE_SLACK_ACCOUNT);
    }
  }
}
