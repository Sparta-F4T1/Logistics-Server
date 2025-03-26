package com.logistic.auth.adapter.in.internal.mapper;

import com.logistic.auth.domain.Passport;
import com.logistic.common.internal.response.AuthClientResponse;
import java.time.Instant;
import java.time.ZoneId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthServiceFactory {
  public static AuthClientResponse toAuthClientSuccessResponse(Passport domainPassport) {
    if (domainPassport == null) {
      throw new IllegalArgumentException("도메인 Passport는 null이 될 수 없습니다");
    }

    return new AuthClientResponse(
        true,
        domainPassport.getUserId().value(),
        createCommonPassport(domainPassport),
        "성공"
    );
  }

  public static com.logistic.common.passport.model.Passport createCommonPassport(Passport domainPassport) {
    if (domainPassport == null) {
      return null;
    }

    com.logistic.common.passport.model.UserInfo userInfo = new com.logistic.common.passport.model.UserInfo(
        domainPassport.getUserId().value(),
        domainPassport.getRole()
    );

    com.logistic.common.passport.model.SessionInfo sessionInfo = new com.logistic.common.passport.model.SessionInfo(
        domainPassport.getSessionId(),
        toInstant(domainPassport.getIssuedAt()),
        toInstant(domainPassport.getExpiresAt())
    );

    return new com.logistic.common.passport.model.Passport(userInfo, sessionInfo);
  }

  public static AuthClientResponse createAuthClientResponse(
      boolean successful,
      String userId,
      Passport domainPassport,
      String message) {

    com.logistic.common.passport.model.Passport commonPassport = domainPassport != null ?
        createCommonPassport(domainPassport) : null;

    return new AuthClientResponse(
        successful,
        userId,
        commonPassport,
        message
    );
  }

  private static Instant toInstant(java.time.LocalDateTime dateTime) {
    if (dateTime == null) {
      return null;
    }
    return dateTime.atZone(ZoneId.systemDefault()).toInstant();
  }
}