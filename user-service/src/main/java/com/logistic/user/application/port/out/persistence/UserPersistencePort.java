package com.logistic.user.application.port.out.persistence;

import com.logistic.user.application.port.in.query.SearchUserQuery;
import com.logistic.user.domain.User;
import org.springframework.data.domain.Page;

public interface UserPersistencePort {
  User save(User user);

  boolean existsByUserId(String userId);

  boolean existsBySlackAccount(String slackAccount);

  User findByUserId(String userId);

  User update(User targetUser);

  void delete(User targetUser, String currentUser);

  Page<User> search(SearchUserQuery query);
}