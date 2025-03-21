package com.logistic.user.application.port.out.persistence;

import com.logistic.user.domain.User;

public interface UserPersistencePort {
  User save(User user);

  boolean existsByUserId(String userId);

  boolean existsBySlackAccount(String slackAccount);

  User findByUserId(String userId);

  User update(User targetUser);

  void delete(User targetUser, String currentUser);
}