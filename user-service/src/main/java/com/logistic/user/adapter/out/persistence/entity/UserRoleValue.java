package com.logistic.user.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserRoleValue {
  @Comment("역할 ID")
  @Column(name = "role_id", nullable = false)
  private Long roleId;

  @Comment("역할 이름")
  @Column(name = "role_name", nullable = false)
  private String name;

  public UserRoleValue(Long roleId, String name) {
    this.roleId = roleId;
    this.name = name;
  }
}