package com.logistic.user.application.service;

import com.logistic.common.passport.model.RoleType;
import com.logistic.user.application.port.in.UserCommandUseCase;
import com.logistic.user.application.port.in.command.RegisterUserCommand;
import com.logistic.user.application.port.out.persistence.UserPersistencePort;
import com.logistic.user.domain.User;
import com.logistic.user.domain.exception.UserServiceErrorCode;
import com.logistic.user.domain.exception.UserServiceException;
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
  private final UserPersistencePort persistencePort;
  private final PasswordEncoder passwordEncoder;

  @Override
  public User registerUser(RegisterUserCommand command) {
    User user = User.create(command.userId(), command.name(), command.password(), command.slackAccount(),
        command.roleId(), command.roleName());

    validateRoleType(command.roleName());
    checkDuplicateUserId(command.userId());
    checkDuplicateSlackAccount(command.slackAccount());

    String hashedPassword = passwordEncoder.encode(user.getPassword().value());
    user.updateHashedPassword(hashedPassword);

    return persistencePort.save(user);
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
}
