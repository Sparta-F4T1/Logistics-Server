package com.logistic.user.adaptor.out.persistence;

import com.logistic.common.annotation.PersistenceAdapter;
import com.logistic.user.application.port.out.UserPersistencePort;
import com.logistic.user.domain.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserPersistenceAdaptor implements UserPersistencePort {
  private final UserJpaRepository jpaRepository;

  @Override
  public Optional<User> save(User user) {

     UserEntity userEntity = jpaRepository.save(UserMapper.mapToUserEntity(user));

     return Optional.ofNullable(UserMapper.toUserEntity(userEntity));
  }


}
