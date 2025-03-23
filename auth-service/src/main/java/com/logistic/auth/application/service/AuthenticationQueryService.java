package com.logistic.auth.application.service;

import com.logistic.auth.application.port.in.AuthenticationQueryUseCase;
import com.logistic.auth.application.port.in.query.VerifyTokenQuery;
import com.logistic.auth.application.port.out.persistence.AuthPersistencePort;
import com.logistic.auth.application.port.out.support.jwt.AuthJwtPort;
import com.logistic.auth.domain.exception.AuthServiceErrorCode;
import com.logistic.auth.domain.exception.AuthServiceException;
import com.logistic.auth.domain.exception.JwtExpiredException;
import com.logistic.auth.domain.exception.JwtParsingException;
import com.logistic.auth.domain.service.TokenValidationResult;
import com.logistic.auth.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthenticationQueryService implements AuthenticationQueryUseCase {

  private final AuthPersistencePort persistencePort;
  private final AuthJwtPort jwtPort;

  @Override
  public UserId validateToken(VerifyTokenQuery query) {
    TokenValidationResult validationResult = this.validateTokenAndExtractId(query.token());
    if (persistencePort.isBlacklisted(validationResult.tokenId())) {
      throw AuthServiceException.auth(AuthServiceErrorCode.BLACKLISTED_TOKEN);
    }
    return validationResult.userId();
  }

  private TokenValidationResult validateTokenAndExtractId(String token) {
    try {
      return jwtPort.validateTokenAndExtractId(token);
    } catch (JwtExpiredException e) {
      log.warn("인증 실패: {} - {}", e.getError().getCode(), e.getError().getMessage());
      throw e;
    } catch (JwtParsingException e) {
      log.warn("토큰 파싱 오류: {} - {}", e.getError().getCode(), e.getError().getMessage());
      throw e;
    }
  }
}