package com.logistic.auth.application.service;

import com.logistic.auth.application.port.in.AuthorizationCommandUseCase;
import com.logistic.auth.application.port.in.command.IssuePassportCommand;
import com.logistic.auth.application.port.out.persistence.AuthPersistencePort;
import com.logistic.auth.application.port.out.support.jwt.AuthJwtPort;
import com.logistic.auth.application.port.out.support.resource.ResourceExtractorPort;
import com.logistic.auth.application.port.out.support.uuid.IdGeneratorPort;
import com.logistic.auth.domain.Passport;
import com.logistic.auth.domain.User;
import com.logistic.auth.domain.exception.AuthServiceErrorCode;
import com.logistic.auth.domain.exception.AuthServiceException;
import com.logistic.auth.domain.exception.JwtExpiredException;
import com.logistic.auth.domain.exception.JwtParsingException;
import com.logistic.auth.domain.service.TokenValidationResult;
import com.logistic.auth.domain.vo.ActionType;
import com.logistic.auth.domain.vo.ResourceType;
import com.logistic.auth.domain.vo.UserId;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class AuthorizationCommandService implements AuthorizationCommandUseCase {
  private final AuthPersistencePort persistencePort;
  private final AuthJwtPort jwtPort;
  private final ResourceExtractorPort resourceExtractor;
  private final IdGeneratorPort idGenerator;

  @Override
  public Passport issuePassport(IssuePassportCommand issuePassportCommand) {
    TokenValidationResult validationResult = validateAndCheckToken(issuePassportCommand.token());
    UserId userId = validationResult.userId();

    Passport cachedPassport = checkCachedPassport(userId);
    if (cachedPassport != null) {
      return cachedPassport;
    }

    User user = persistencePort.findByUserIdWithPermission(userId);

    checkPermission(user, issuePassportCommand.uri(), issuePassportCommand.method());

    Passport passport = createAndSavePassport(user);

    log.info("사용자 {}에 대한 패스포트가 성공적으로 발급되었습니다.", userId);
    return passport;
  }

  private TokenValidationResult validateAndCheckToken(String token) {
    TokenValidationResult validationResult = validateTokenAndExtractId(token);
    if (persistencePort.isBlacklisted(validationResult.tokenId())) {
      throw AuthServiceException.auth(AuthServiceErrorCode.BLACKLISTED_TOKEN);
    }
    return validationResult;
  }

  private Passport checkCachedPassport(UserId userId) {
    try {
      Passport cachedPassport = persistencePort.findPassportFromCache(userId);
      if (cachedPassport != null && !cachedPassport.isExpired(LocalDateTime.now())) {
        return cachedPassport;
      }
    } catch (AuthServiceException e) {
      log.error(e.getMessage());
    }
    return null;
  }

  private void checkPermission(User user, String uri, String method) {
    try {
      ResourceType resourceType = resourceExtractor.extractResourceType(uri);
      ActionType actionType = resourceExtractor.extractActionType(method);

      log.debug("추출된 요청 정보: resourceType={}, actionType={}",
          resourceType.name(), actionType.name());

      if (!user.hasPermission(resourceType, actionType)) {
        String message = "사용자 %s에게 필요한 권한이 없습니다: resource: %s, action: %s".formatted(user.getUserId().value(),
            resourceType.name(),
            actionType.name());
        log.warn(message);
        throw AuthServiceException.auth(AuthServiceErrorCode.INSUFFICIENT_PERMISSIONS, ": " + message);
      }
    } catch (AuthServiceException e) {
      log.warn(e.getMessage());
      throw e;
    }
  }

  private Passport createAndSavePassport(User user) {
    String sessionId = idGenerator.generateUniqueId();
    LocalDateTime now = LocalDateTime.now();
    Passport passport = Passport.create(user, sessionId, now);

    persistencePort.savePassport(passport);
    return passport;
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