package com.logistic.user.adapter.out.persistence.repository;

import com.logistic.user.adapter.out.persistence.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJapRepository extends JpaRepository<UserEntity, String> {
  boolean existsByUserIdAndDeletedAtIsNull(String userId);

  boolean existsBySlackAccountAndDeletedAtIsNull(String slackAccount);

  Optional<UserEntity> findByUserIdAndDeletedAtIsNull(String userId);
}