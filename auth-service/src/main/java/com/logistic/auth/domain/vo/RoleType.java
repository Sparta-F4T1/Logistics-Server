package com.logistic.auth.domain.vo;

public enum RoleType {
  MASTER_ADMIN("마스터 관리자", (resourceType, actionType, resourceId) -> true),

  HUB_ADMIN("허브 관리자", (resourceType, actionType, resourceId) ->
      !resourceType.equals("HUB") || resourceId == null),

  DELIVERY_PERSONNEL("배송 담당자", (resourceType, actionType, resourceId) ->
      !resourceType.equals("DELIVERY") || resourceId == null),

  COMPANY_PERSONNEL("회사 담당자", (resourceType, actionType, resourceId) ->
      !resourceType.equals("COMPANY") || resourceId == null);

  private final String description;
  private final ResourceAccessChecker accessChecker;

  RoleType(String description, ResourceAccessChecker accessChecker) {
    this.description = description;
    this.accessChecker = accessChecker;
  }

  public String getDescription() {
    return description;
  }

  public RoleName toRoleName() {
    return RoleName.of(this);
  }

  public boolean hasResourceAccess(String resourceType, Long resourceId) {
    return accessChecker.hasAccess(resourceType, null, resourceId);
  }

  @FunctionalInterface
  interface ResourceAccessChecker {
    boolean hasAccess(String resourceType, String actionType, Long resourceId);
  }
}