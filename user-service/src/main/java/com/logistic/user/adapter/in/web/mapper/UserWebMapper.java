package com.logistic.user.adapter.in.web.mapper;

import com.logistic.common.passport.model.Passport;
import com.logistic.user.adapter.in.web.request.RegisterUserRequest;
import com.logistic.user.adapter.in.web.request.UpdateUserRequest;
import com.logistic.user.adapter.in.web.response.FindUserResponse;
import com.logistic.user.adapter.in.web.response.UpdateUserResponse;
import com.logistic.user.application.port.in.command.RegisterUserCommand;
import com.logistic.user.application.port.in.command.UpdateUserCommand;
import com.logistic.user.application.port.in.query.FindUserQuery;
import com.logistic.user.domain.User;
import com.logistic.user.domain.vo.Email;
import com.logistic.user.domain.vo.Name;
import com.logistic.user.domain.vo.Password;
import com.logistic.user.domain.vo.UserId;
import com.logistic.user.domain.vo.UserStatus;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserWebMapper {
  @Mapping(target = "userId", expression = "java(request.userId())")
  @Mapping(target = "name", expression = "java(request.name())")
  @Mapping(target = "password", expression = "java(request.password())")
  @Mapping(target = "confirmedPassword", expression = "java(request.confirmedPassword())")
  @Mapping(target = "slackAccount", expression = "java(request.slackAccount())")
  @Mapping(target = "roleId", expression = "java(request.roleId())")
  @Mapping(target = "roleName", expression = "java(request.roleName())")
  @Mapping(target = "currentUserId", expression = "java(extractUserId(passport))")
  @Mapping(target = "currentUserRole", expression = "java(extractUserRole(passport))")
  RegisterUserCommand toCreateCommand(@Valid RegisterUserRequest request, Passport passport);

  @Mapping(target = "userId", expression = "java(map(user.getUserId()))")
  @Mapping(target = "name", expression = "java(map(user.getName()))")
  @Mapping(target = "slackAccount", expression = "java(map(user.getSlackAccount()))")
  @Mapping(target = "roleId", expression = "java(user.getRole().roleId())")
  @Mapping(target = "roleName", expression = "java(user.getRole().name())")
  @Mapping(target = "status", expression = "java(user.getStatus().name())")
  FindUserResponse toUserResponse(User user);

  @Mapping(target = "userId", expression = "java(userId)")
  @Mapping(target = "currentUserId", expression = "java(extractUserId(passport))")
  @Mapping(target = "currentUserRole", expression = "java(extractUserRole(passport))")
  FindUserQuery toFindQuery(String userId, Passport passport);

  @Mapping(target = "targetUserId", expression = "java(userId)")
  @Mapping(target = "password", expression = "java(request.password())")
  @Mapping(target = "confirmedPassword", expression = "java(request.confirmedPassword())")
  @Mapping(target = "slackAccount", expression = "java(request.slackAccount())")
  @Mapping(target = "currentUserId", expression = "java(extractUserId(passport))")
  @Mapping(target = "currentUserRole", expression = "java(extractUserRole(passport))")
  UpdateUserCommand toUpdateCommand(String userId, UpdateUserRequest request, Passport passport);

  @Mapping(target = "userId", expression = "java(map(user.getUserId()))")
  @Mapping(target = "slackAccount", expression = "java(map(user.getSlackAccount()))")
  UpdateUserResponse toUpdateResponse(User user);

  default String extractUserId(Passport passport) {
    return passport != null && passport.getUserInfo() != null ?
        passport.getUserInfo().getUserId() : null;
  }

  default String extractUserRole(Passport passport) {
    return passport != null && passport.getUserInfo() != null ?
        passport.getUserInfo().getRole() : null;
  }

  default String map(UserId userId) {
    return userId != null ? userId.value() : null;
  }

  default String map(Name name) {
    return name != null ? name.value() : null;
  }

  default String map(Email email) {
    return email != null ? email.value() : null;
  }

  default String map(Password password) {
    return password != null ? password.value() : null;
  }

  default String map(UserStatus status) {
    return status != null ? status.name() : null;
  }
}