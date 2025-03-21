package com.logistic.user.application.service;

import com.logistic.user.application.port.in.UserQueryUseCase;
import com.logistic.user.application.port.in.query.FindUserQuery;
import com.logistic.user.application.port.out.persistence.UserPersistencePort;
import com.logistic.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService implements UserQueryUseCase {
  // TODO: 추가 권한 체크
  private final UserPersistencePort persistencePort;

  @Override
  public User findUser(FindUserQuery findQuery) {
    return persistencePort.findByUserId(findQuery.userId());
  }
}