package com.logistic.auth.application.port.in;

import com.logistic.auth.application.port.in.command.LoginCommand;
import com.logistic.auth.domain.TokenPair;

public interface AuthCommandUseCase {
  TokenPair login(LoginCommand command);
}
