package com.logistic.user.adaptor.out.persistence;

import com.logistic.user.domain.UserRole;
import jakarta.persistence.Column;
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
@Table(name = "p_user")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {
  @Id
  @Column(name = "user_id",length = 10)
  @Comment("사용자 기본키")
  private String userId;

  @Column(name = "password", nullable = false)
  @Comment("비밀번호")
  private String password;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  @Comment("권한")
  private UserRole role;

  @Column(name = "slack_account", nullable = false)
  @Comment("슬랙 계정")
  private String slackAccount;

  @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
  @Comment("활성화 여부")
  private boolean isActive;
}