package com.logistic.auth.domain;

import com.logistic.auth.domain.vo.ActionType;
import com.logistic.auth.domain.vo.Password;
import com.logistic.auth.domain.vo.ResourceType;
import com.logistic.auth.domain.vo.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
  private final UserId userId;
  private Password password;
  private Role role;

  public static User create(String userId, String encodedPassword, Role role) {
    return User.builder()
        .userId(UserId.of(userId))
        .password(Password.of(encodedPassword))
        .role(role)
        .build();
  }

  public boolean hasPermission(String resourceType, String actionType) {
    return role.hasPermission(
        ResourceType.fromString(resourceType),
        ActionType.fromString(actionType)
    );
  }

  public boolean hasPermission(ResourceType resourceType, ActionType actionType) {
    return role.hasPermission(resourceType, actionType);
  }

  public boolean hasResourceAccess(String resourceType, Long resourceId) {
    return role.hasResourceAccess(resourceType, resourceId);
  }

  public void updatePassword(String encodedPassword) {
    this.password = Password.of(encodedPassword);
  }

  public void assignRole(Role newRole) {
    this.role = newRole;
  }
}