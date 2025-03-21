package com.logistic.user.adapter.in.web.mapper;

import com.logistic.user.adapter.in.web.request.RegisterUserRequest;
import com.logistic.user.adapter.in.web.response.FindUserResponse;
import com.logistic.user.application.port.in.command.RegisterUserCommand;
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

  RegisterUserCommand toCreateCommand(@Valid RegisterUserRequest request);

  @Mapping(target = "userId", expression = "java(map(user.getUserId()))")
  @Mapping(target = "name", expression = "java(map(user.getName()))")
  @Mapping(target = "slackAccount", expression = "java(map(user.getSlackAccount()))")
  @Mapping(target = "roleId", expression = "java(user.getRole().roleId())")
  @Mapping(target = "roleName", expression = "java(user.getRole().name())")
  @Mapping(target = "status", expression = "java(user.getStatus().name())")
  FindUserResponse toUserResponse(User user);

  // Value Object 타입 변환을 위한 매핑 메서드들
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