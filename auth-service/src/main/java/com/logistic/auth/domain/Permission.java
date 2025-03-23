package com.logistic.auth.domain;

import com.logistic.auth.domain.vo.ActionType;
import com.logistic.auth.domain.vo.PermissionId;
import com.logistic.auth.domain.vo.ResourceType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Permission {
  private final PermissionId permissionId;
  private ResourceType resourceType;
  private ActionType actionType;
  private String description;

  public static Permission create(Long id, ResourceType resourceType, ActionType actionType, String description) {
    return Permission.builder()
        .permissionId(PermissionId.of(id))
        .resourceType(ResourceType.fromString(resourceType.name()))
        .actionType(ActionType.fromString(actionType.name()))
        .description(description)
        .build();
  }

  public String getPermissionName() {
    return resourceType.name() + ":" + actionType.name();
  }
}