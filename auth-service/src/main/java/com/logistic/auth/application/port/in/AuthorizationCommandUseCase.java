package com.logistic.auth.application.port.in;

import com.logistic.auth.application.port.in.command.IssuePassportCommand;
import com.logistic.auth.domain.Passport;

public interface AuthorizationCommandUseCase {
  Passport issuePassport(IssuePassportCommand issuePassportCommand);
}