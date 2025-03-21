package com.logistic.user.adapter.out.persistence;

import com.logistic.common.annotation.Adapter;
import com.logistic.user.adapter.out.persistence.entity.UserEntity;
import com.logistic.user.adapter.out.persistence.mapper.UserPersistenceMapper;
import com.logistic.user.adapter.out.persistence.repository.UserJapRepository;
import com.logistic.user.application.port.out.persistence.UserPersistencePort;
import com.logistic.user.domain.User;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {
  private final UserJapRepository userJapRepository;
  private final UserPersistenceMapper mapper;


  @Override
  public boolean existsByUserId(String userId) {
    return userJapRepository.existsByUserId(userId);
  }

  @Override
  public boolean existsBySlackAccount(String slackAccount) {
    return userJapRepository.existsBySlackAccount(slackAccount);
  }

  @Override
  public User save(User user) {
    UserEntity savedUserEntity = userJapRepository.save(mapper.toEntity(user));
    return mapper.toDomain(savedUserEntity);
  }
}
