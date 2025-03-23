package com.logistic.auth.adapter.out.persistence.mapper;

import com.logistic.auth.adapter.out.persistence.model.PermissionEntity;
import com.logistic.auth.adapter.out.persistence.model.RoleEntity;
import com.logistic.auth.adapter.out.persistence.model.RolePermissionEntity;
import com.logistic.auth.adapter.out.persistence.model.UserEntity;
import com.logistic.auth.adapter.out.persistence.model.vo.PasswordValue;
import com.logistic.auth.domain.Permission;
import com.logistic.auth.domain.Role;
import com.logistic.auth.domain.User;
import com.logistic.auth.domain.vo.RoleType;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class AuthPersistenceMapper {
  public UserEntity toEntity(User user) {
    if (user == null) {
      return null;
    }

    return UserEntity.builder()
        .id(user.getUserId().value())
        .password(new PasswordValue(user.getPassword().getHashedValue()))
        .role(toEntity(user.getRole()))
        .build();
  }

  public User toLoginDomain(UserEntity entity) {
    if (entity == null) {
      return null;
    }

    return User.create(
        entity.getId(),
        entity.getPassword().getHashedValue(),
        null
    );
  }

  public User toDomain(UserEntity entity) {
    if (entity == null) {
      return null;
    }

    return User.create(
        entity.getId(),
        entity.getPassword().getHashedValue(),
        toDomain(entity.getRole())
    );
  }

  public RoleEntity toEntity(Role role) {
    if (role == null) {
      return null;
    }

    RoleType roleType = null;
    try {
      roleType = RoleType.valueOf(role.getName().getValue());
    } catch (IllegalArgumentException e) {
    }

    RoleEntity roleEntity = RoleEntity.builder()
        .id(role.getRoleId().getValue())
        .name(roleType)
        .description(role.getDescription())
        .rolePermissions(new HashSet<>())
        .build();

    if (role.getPermissions() != null) {
      for (Permission permission : role.getPermissions()) {
        PermissionEntity permissionEntity = toEntity(permission);
        if (permissionEntity != null) {
          RolePermissionEntity rolePermission = RolePermissionEntity.of(roleEntity, permissionEntity);
          roleEntity.getRolePermissions().add(rolePermission);
        }
      }
    }

    return roleEntity;
  }

  public Role toDomain(RoleEntity entity) {
    if (entity == null) {
      return null;
    }

    Role role = Role.create(
        entity.getId(),
        entity.getName()
    );

    if (entity.getRolePermissions() != null) {
      for (RolePermissionEntity rp : entity.getRolePermissions()) {
        if (rp.getPermission() != null) {
          Permission permission = toDomain(rp.getPermission());
          if (permission != null) {
            role.addPermission(permission);
          }
        }
      }
    }

    return role;
  }

  public PermissionEntity toEntity(Permission permission) {
    if (permission == null) {
      return null;
    }

    // 빌더 패턴 사용
    return PermissionEntity.builder()
        .id(permission.getPermissionId().getValue())
        .resourceType(permission.getResourceType())
        .actionType(permission.getActionType())
        .description(permission.getDescription())
        .rolePermissions(new HashSet<>())
        .build();
  }

  public Permission toDomain(PermissionEntity entity) {
    if (entity == null) {
      return null;
    }

    return Permission.create(
        entity.getId(),
        entity.getResourceType(),
        entity.getActionType(),
        entity.getDescription()
    );
  }

  public List<User> toDomainList(List<UserEntity> entities) {
    if (entities == null) {
      return null;
    }
    return entities.stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  public List<UserEntity> toEntityList(List<User> domains) {
    if (domains == null) {
      return null;
    }
    return domains.stream()
        .map(this::toEntity)
        .collect(Collectors.toList());
  }

  public List<Role> toDomainRoleList(List<RoleEntity> entities) {
    if (entities == null) {
      return null;
    }
    return entities.stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  public List<Permission> toDomainPermissionList(List<PermissionEntity> entities) {
    if (entities == null) {
      return null;
    }
    return entities.stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }
}