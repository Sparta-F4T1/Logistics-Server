package com.logistic.user.adapter.in.web.mapper;

import com.logistic.user.adapter.in.web.request.RegisterUserRequest;
import com.logistic.user.adapter.in.web.response.FindUserResponse;
import com.logistic.user.application.port.in.command.RegisterUserCommand;
import com.logistic.user.domain.User;
import com.logistic.user.domain.vo.Email;
import com.logistic.user.domain.vo.Name;
import com.logistic.user.domain.vo.UserId;
import com.logistic.user.domain.vo.UserStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserWebMapper {
  RegisterUserCommand toCreateCommand(RegisterUserRequest request);

  FindUserResponse toUserResponse(User user);

  default String map(UserId userId) {
    return userId != null ? userId.value() : null;
  }

  default String map(Name name) {
    return name != null ? name.value() : null;
  }

  default String map(Email email) {
    return email != null ? email.value() : null;
  }

  default String map(UserStatus status) {
    return status != null ? status.name() : null;
  }
}