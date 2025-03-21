package com.logistic.user.application.port.in;

import com.logistic.user.application.port.in.command.DeleteUserCommand;
import com.logistic.user.application.port.in.command.RegisterUserCommand;
import com.logistic.user.application.port.in.command.UpdateUserCommand;
import com.logistic.user.application.port.in.command.UpdateUserStatusCommand;
import com.logistic.user.domain.User;
import com.logistic.user.domain.vo.UserStatus;

public interface UserCommandUseCase {
  User registerUser(RegisterUserCommand command);

  User updateUser(UpdateUserCommand updateCommand);

  UserStatus updateUserStatus(UpdateUserStatusCommand updateStatusCommand);

  void deleteUser(DeleteUserCommand deleteCommand);
}