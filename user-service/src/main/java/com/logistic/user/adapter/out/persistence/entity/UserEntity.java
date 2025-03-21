package com.logistic.user.adapter.out.persistence.entity;

import com.logistic.user.domain.vo.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Builder
@Table(name = "p_user")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {
  @Id
  @Comment("사용자 고유 식별자")
  @Column(name = "user_id", nullable = false, unique = true)
  private String userId;

  @Comment("사용자 이름")
  @Column(name = "name", nullable = false)
  private String name;

  @Comment("비밀번호")
  @Column(name = "password", nullable = false)
  private String password;

  @Comment("슬랙 계정")
  @Column(name = "slack_account", nullable = false, unique = true)
  private String slackAccount;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  @Comment("사용자 상태 (ACTIVE, INACTIVE, LOCKED, PENDING)")
  private UserStatus status = UserStatus.ACTIVE;

  @Embedded
  private UserRoleValue role;
}