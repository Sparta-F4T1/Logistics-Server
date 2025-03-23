package com.logistic.auth.adapter.out.persistence.model;

import com.logistic.auth.adapter.out.persistence.model.vo.PasswordValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "p_auth_user")
@Comment("사용자 정보 테이블")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {
  @Id
  @Column(name = "user_id")
  @Comment("사용자 식별자")
  private String id;

  @Embedded
  @Comment("비밀번호 정보")
  private PasswordValue password;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id", nullable = false)
  @Comment("역할 식별자 (외래키)")
  private RoleEntity role;

  public void updatePassword(String hashedPassword) {
    this.password = new PasswordValue(hashedPassword);
  }

  public void updateRole(RoleEntity role) {
    this.role = role;
  }
}