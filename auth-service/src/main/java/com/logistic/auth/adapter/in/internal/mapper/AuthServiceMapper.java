package com.logistic.auth.adapter.in.internal.mapper;

import com.logistic.auth.application.port.in.command.IssuePassportCommand;
import com.logistic.auth.application.port.in.query.VerifyTokenQuery;
import com.logistic.auth.domain.Passport;
import com.logistic.auth.domain.Role;
import com.logistic.auth.domain.vo.ActionType;
import com.logistic.auth.domain.vo.ResourceType;
import com.logistic.auth.domain.vo.RoleType;
import com.logistic.auth.domain.vo.UserId;
import com.logistic.auth.domain.vo.UserInfo;
import com.logistic.common.internal.request.AuthClientRequest;
import com.logistic.common.internal.response.AuthClientResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthServiceMapper {
  AuthServiceMapper INSTANCE = Mappers.getMapper(AuthServiceMapper.class);

  VerifyTokenQuery toVerifyTokenQuery(AuthClientRequest request);

  @Mapping(target = "userId", source = "value")
  @Mapping(target = "successful", constant = "true")
  AuthClientResponse toAuthClientSuccessResponse(UserId subject);

  /**
   * 도메인 Passport를 이용해 성공 응답을 생성합니다.
   */
  @Mapping(target = "successful", constant = "true")
  @Mapping(source = "userInfo.userId.value", target = "userId")
  @Mapping(source = ".", target = "passport", qualifiedByName = "passportToDtoPassport")
  @Mapping(target = "message", constant = "성공")
  AuthClientResponse toAuthClientSuccessResponse(Passport domainPassport);

  IssuePassportCommand toIssuePassportCommand(AuthClientRequest request);

  @Mapping(source = "userInfo", target = "userInfo")
  @Mapping(source = "sessionId", target = "sessionInfo.sessionId")
  @Mapping(source = "issuedAt", target = "sessionInfo.issuedAt", qualifiedByName = "localDateTimeToInstant")
  @Mapping(source = "expiresAt", target = "sessionInfo.expiresAt", qualifiedByName = "localDateTimeToInstant")
  com.logistic.common.passport.model.Passport toCommonPassport(Passport domainPassport);

  @Mapping(source = "userId.value", target = "userId")
  @Mapping(source = "role", target = "role")
  com.logistic.common.passport.model.UserInfo toCommonUserInfo(UserInfo domainUserInfo);

  /**
   * Role 도메인 객체에서 RoleType enum으로 변환
   */
  @Named("roleToRoleType")
  default RoleType roleToRoleType(Role role) {
    if (role == null || role.getName() == null) {
      return null;
    }
    try {
      return RoleType.valueOf(role.getName().getValue());
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  /**
   * 문자열에서 ResourceType enum으로 변환
   */
  @Named("stringToResourceType")
  default ResourceType stringToResourceType(String resourceType) {
    if (resourceType == null) {
      return null;
    }
    return ResourceType.fromString(resourceType);
  }

  /**
   * 문자열에서 ActionType enum으로 변환
   */
  @Named("stringToActionType")
  default ActionType stringToActionType(String actionType) {
    if (actionType == null) {
      return null;
    }
    return ActionType.fromString(actionType);
  }

  @Named("localDateTimeToInstant")
  default Instant localDateTimeToInstant(LocalDateTime dateTime) {
    if (dateTime == null) {
      return null;
    }
    return dateTime.atZone(ZoneId.systemDefault()).toInstant();
  }

  @Named("passportToDtoPassport")
  default com.logistic.common.passport.model.Passport passportToDtoPassport(Passport domainPassport) {
    return toCommonPassport(domainPassport);
  }
}