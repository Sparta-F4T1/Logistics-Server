package com.logistic.auth.application.port.in;

import com.logistic.auth.application.port.in.query.VerifyTokenQuery;
import com.logistic.auth.domain.vo.UserId;

public interface AuthQueryUseCase {
  UserId validateToken(VerifyTokenQuery verifyTokenQuery);
}
