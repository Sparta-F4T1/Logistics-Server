package com.logistic.user.adapter.out.persistence.mapper;

import com.logistic.user.adapter.out.persistence.entity.UserEntity;
import com.logistic.user.adapter.out.persistence.entity.UserRoleValue;
import com.logistic.user.domain.User;
import com.logistic.user.domain.vo.Email;
import com.logistic.user.domain.vo.Name;
import com.logistic.user.domain.vo.Password;
import com.logistic.user.domain.vo.UserId;
import com.logistic.user.domain.vo.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", imports = {UserId.class, Name.class, Password.class, Email.class, UserRole.class})
public interface UserPersistenceMapper {

  UserPersistenceMapper INSTANCE = Mappers.getMapper(UserPersistenceMapper.class);

  @Mapping(target = "userId", source = "userId.value")
  @Mapping(target = "name", source = "name.value")
  @Mapping(target = "password", source = "password.value")
  @Mapping(target = "slackAccount", source = "slackAccount.value")
  UserEntity toEntity(User user);

  @Mapping(target = "userId", expression = "java(UserId.of(entity.getUserId()))")
  @Mapping(target = "name", expression = "java(Name.of(entity.getName()))")
  @Mapping(target = "password", expression = "java(Password.ofHashedPassword(entity.getPassword()))")
  @Mapping(target = "slackAccount", expression = "java(entity.getSlackAccount() != null ? Email.of(entity.getSlackAccount()) : null)")
  @Mapping(target = "role", expression = "java(UserRole.of(entity.getRole().getRoleId(), entity.getRole().getName()))")
  @Mapping(target = "status", expression = "java(entity.getStatus())")
  User toDomain(UserEntity entity);

  default UserRoleValue toUserRoleValue(UserRole role) {
    if (role == null) {
      return null;
    }
    return new UserRoleValue(role.roleId(), role.name());
  }
}