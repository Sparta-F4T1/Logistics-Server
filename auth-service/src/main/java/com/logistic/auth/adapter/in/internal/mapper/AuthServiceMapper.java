package com.logistic.auth.adapter.in.internal.mapper;

import com.logistic.auth.application.port.in.query.VerifyTokenQuery;
import com.logistic.auth.domain.vo.UserId;
import com.logistic.common.internal.request.AuthClientRequest;
import com.logistic.common.internal.response.AuthClientResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface AuthServiceMapper {
  VerifyTokenQuery toVerifyTokenQuery(AuthClientRequest request);

  @Mapping(target = "userId", source = "value")
  @Mapping(target = "successful", constant = "true")
  AuthClientResponse toAuthClientSuccessResponse(UserId subject);
}
