package com.logistic.user.application.port.in;

import com.logistic.user.adapter.in.web.mapper.UserWebMapper;
import com.logistic.user.application.port.in.query.FindUserQuery;
import com.logistic.user.domain.User;

public interface UserQueryUseCase {
  User findUser(FindUserQuery findQuery);

  UserWebMapper search(Object searchQuery);
}