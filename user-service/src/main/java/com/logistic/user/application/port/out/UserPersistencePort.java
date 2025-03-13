package com.logistic.user.application.port.out;

import com.logistic.user.domain.User;
import java.util.Optional;

public interface UserPersistencePort {
  Optional<User> save(User user);
}
