package com.logistic.user.adapter.out.persistence.repository;

import com.logistic.user.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJapRepository extends JpaRepository<UserEntity, String> {
  boolean existsByUserId(String userId);

  boolean existsBySlackAccount(String slackAccount);
}