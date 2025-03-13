package com.logistic.user.application.port.in;

import com.logistic.user.application.port.in.command.TestCommand;

public interface UserUseCase {
  String createUser(TestCommand command);
}
