package com.logistic.auth.domain;

import com.logistic.auth.domain.vo.ActionType;
import com.logistic.auth.domain.vo.ResourceType;
import com.logistic.auth.domain.vo.RoleId;
import com.logistic.auth.domain.vo.RoleName;
import com.logistic.auth.domain.vo.RoleType;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Role {
  private final RoleId roleId;
  private RoleName name;
  private String description;
  private Set<Permission> permissions;

  public static Role create(Long id, RoleType roleType) {
    return Role.builder()
        .roleId(RoleId.of(id))
        .name(RoleName.of(roleType))
        .description(roleType.getDescription())
        .permissions(new HashSet<>())
        .build();
  }

  public static Role create(Long id, String name, String description) {
    return Role.builder()
        .roleId(RoleId.of(id))
        .name(RoleName.of(name))
        .description(description)
        .permissions(new HashSet<>())
        .build();
  }

  public void addPermission(Permission permission) {
    if (permissions == null) {
      permissions = new HashSet<>();
    }
    permissions.add(permission);
  }

  public boolean hasPermission(ResourceType resourceType, ActionType actionType) {
    if (permissions == null) {
      return false;
    }

    // MASTER_ADMIN은 모든 권한 가짐
    if (this.name.getValue().equals("MASTER_ADMIN")) {
      return true;
    }

    return permissions.stream()
        .anyMatch(p -> p.getResourceType().equals(resourceType)
            && p.getActionType().equals(actionType));
  }

  public RoleType getRoleType() {
    try {
      return RoleType.valueOf(name.getValue());
    } catch (IllegalArgumentException e) {
      return null; // 사용자 정의 역할은 열거형에 없음
    }
  }

  public boolean hasResourceAccess(String resourceType, Long resourceId) {
    RoleType roleType = getRoleType();
    if (roleType == null) {
      // 열거형에 없는 역할은 기본 동작 정의
      return false; // 사용자 정의 역할은 기본적으로 제한적 접근
    }

    return roleType.hasResourceAccess(resourceType, resourceId);
  }

  public Set<Permission> getPermissions() {
    return Collections.unmodifiableSet(permissions);
  }
}