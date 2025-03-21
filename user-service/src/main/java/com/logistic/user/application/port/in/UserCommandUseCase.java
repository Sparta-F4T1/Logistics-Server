package com.logistic.user.application.port.in;

import com.logistic.user.domain.User;

public interface UserCommandUseCase {
  User createUser(Object createCommand);
}