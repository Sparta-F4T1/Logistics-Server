package com.logistic.user.application.port.in;

import com.logistic.user.application.port.in.command.RegisterUserCommand;
import com.logistic.user.domain.User;

public interface UserCommandUseCase {
  User registerUser(RegisterUserCommand command);
}