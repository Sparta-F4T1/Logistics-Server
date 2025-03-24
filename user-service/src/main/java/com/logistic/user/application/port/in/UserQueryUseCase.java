package com.logistic.user.application.port.in;

import com.logistic.user.application.port.in.query.FindUserQuery;
import com.logistic.user.application.port.in.query.SearchUserQuery;
import com.logistic.user.domain.User;
import org.springframework.data.domain.Page;

public interface UserQueryUseCase {
  User findUser(FindUserQuery findQuery);

  Page<User> search(SearchUserQuery searchQuery);
}