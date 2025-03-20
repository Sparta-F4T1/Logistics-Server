package com.logistic.auth.application.service;

import com.logistic.auth.application.port.in.AuthCommandUseCase;
import com.logistic.auth.application.port.in.command.LoginCommand;
import com.logistic.auth.application.port.in.command.RefreshCommand;
import com.logistic.auth.application.port.out.persistence.AuthPersistencePort;
import com.logistic.auth.application.port.out.support.jwt.AuthJwtPort;
import com.logistic.auth.application.port.out.support.uuid.IdGeneratorPort;
import com.logistic.auth.domain.TokenPair;
import com.logistic.auth.domain.User;
import com.logistic.auth.domain.exception.AuthServiceErrorCode;
import com.logistic.auth.domain.exception.AuthServiceException;
import com.logistic.auth.domain.exception.JwtExpiredException;
import com.logistic.auth.domain.exception.JwtParsingException;
import com.logistic.auth.domain.service.TokenValidationResult;
import com.logistic.auth.domain.vo.TokenId;
import com.logistic.auth.domain.vo.UserId;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class AuthCommandService implements AuthCommandUseCase {
  private final AuthPersistencePort persistencePort;
  private final AuthJwtPort jwtPort;
  private final IdGeneratorPort idGenerator;
  private final PasswordEncoder passwordEncoder;

  @Override
  public TokenPair login(LoginCommand command) {
//    User user = persistencePort.findByUserId(command.username());
    User user = User.mock();

//    if (!passwordEncoder.matches(command.password(), user.getPassword().getHashedValue())) {
    if (!true) {
      throw new AuthServiceException(AuthServiceErrorCode.USER_NOT_FOUND);
    }

    String tokenId = idGenerator.generateUniqueId();
    TokenPair tokenPair = jwtPort.createTokenPair(tokenId, user.getUserId());
    persistencePort.saveRefreshToken(
        tokenPair.getTokenId(),
        user.getUserId(),
        tokenPair.getRefreshTokenCredential()
    );

    log.info("로그인 성공: {}", user.getUserId().value());
    return tokenPair;
  }

  @Override
  public TokenPair refresh(RefreshCommand command) {
    String refreshToken = command.refreshToken();
    TokenValidationResult validationResult = validateToken(refreshToken);

    UserId userId = validationResult.userId();
    TokenId oldTokenId = validationResult.tokenId();
    boolean isValidRefreshToken = persistencePort.isValidRefreshToken(oldTokenId, refreshToken);
    if (!isValidRefreshToken) {
      throw new AuthServiceException(AuthServiceErrorCode.REFRESH_TOKEN_MISMATCH);
    }

    Instant expirationTime = jwtPort.getExpirationTime(command.accessToken());
    if (expirationTime.isAfter(Instant.now())) {
      persistencePort.addToBlacklist(oldTokenId, expirationTime);
      persistencePort.removeRefreshToken(oldTokenId, userId);
    }

    String sessionId = idGenerator.generateUniqueId();
    TokenPair newTokenPair = jwtPort.createTokenPair(sessionId, userId);
    persistencePort.saveRefreshToken(newTokenPair.getTokenId(), userId, newTokenPair.getRefreshTokenCredential());

    log.info("토큰 갱신 성공: {}", userId.value());
    return newTokenPair;
  }

  private TokenValidationResult validateToken(String token) {
    try {
      return jwtPort.validateTokenAndExtractId(token);
    } catch (JwtExpiredException e) {
      log.warn("만료된 토큰: {} - {}", e.getError().getCode(), e.getError().getMessage());
      throw e;
    } catch (JwtParsingException e) {
      log.warn("토큰 파싱 오류: {} - {}", e.getError().getCode(), e.getError().getMessage());
      throw e;
    }
  }
}