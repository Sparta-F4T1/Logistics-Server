package com.logistic.auth.adapter.out.persistence.repository;

import com.logistic.auth.adapter.out.persistence.model.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {

  @Query("SELECT u FROM UserEntity u " +
      "JOIN FETCH u.role r " +
      "LEFT JOIN FETCH r.rolePermissions rp " +
      "LEFT JOIN FETCH rp.permission " +
      "WHERE u.id = :userId AND u.deletedAt IS NULL")
  Optional<UserEntity> findByIdWithRoleAndPermissions(@Param("userId") String userId);

  @EntityGraph(attributePaths = {"password"})
  @Query("SELECT u FROM UserEntity u WHERE u.id = :userId AND u.deletedAt IS NULL")
  Optional<UserEntity> findByIdForLogin(@Param("userId") String userId);
}