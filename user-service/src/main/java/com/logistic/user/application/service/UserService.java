package com.logistic.user.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.user.application.port.in.UserUseCase;
import com.logistic.user.application.port.in.command.TestCommand;
import com.logistic.user.application.port.out.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class UserService implements UserUseCase {
  private final UserPersistencePort userPersistencePort;

  @Override
  public String createUser(TestCommand command) {
    return "";
  }
}
