package com.logistic.auth.application.service;

import com.logistic.auth.application.port.in.AuthCommandUseCase;
import com.logistic.auth.application.port.in.command.LoginCommand;
import com.logistic.auth.application.port.in.command.LogoutCommand;
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
    User user = persistencePort.findByUserId(command.username());

    if (!passwordEncoder.matches(command.password(), user.getPassword().getHashedValue())) {
      throw new AuthServiceException(AuthServiceErrorCode.USER_NOT_FOUND);
    }

    TokenPair tokenPair = createAndSaveTokenPair(user.getUserId());
    log.info("로그인 성공: {}", user.getUserId().value());

    return tokenPair;
  }

  @Override
  public TokenPair refresh(RefreshCommand command) {
    String refreshToken = command.refreshToken();
    TokenValidationResult validationResult = validateToken(refreshToken);

    UserId userId = validationResult.userId();
    TokenId oldTokenId = validationResult.tokenId();

    validateRefreshToken(oldTokenId, refreshToken);
    invalidateOldTokenIfNeeded(command.accessToken(), oldTokenId, userId);

    TokenPair newTokenPair = createAndSaveTokenPair(userId);
    log.info("토큰 갱신 성공: {}", userId.value());

    return newTokenPair;
  }

  @Override
  public void logout(LogoutCommand command) {
    TokenValidationResult validationResult = validateAndCheckBlacklist(command.accessToken());
    invalidateToken(validationResult.tokenId(), validationResult.userId(), validationResult.expiration());
    log.info("로그아웃 성공: TokenId={}", validationResult.tokenId().value());
  }

  private TokenPair createAndSaveTokenPair(UserId userId) {
    String tokenId = idGenerator.generateUniqueId();
    TokenPair tokenPair = jwtPort.createTokenPair(tokenId, userId);
    persistencePort.saveRefreshToken(
        tokenPair.getTokenId(),
        userId,
        tokenPair.getRefreshTokenCredential()
    );
    return tokenPair;
  }

  private void validateRefreshToken(TokenId tokenId, String refreshToken) {
    boolean isValidRefreshToken = persistencePort.isValidRefreshToken(tokenId, refreshToken);
    if (!isValidRefreshToken) {
      throw new AuthServiceException(AuthServiceErrorCode.REFRESH_TOKEN_MISMATCH);
    }
  }

  private void invalidateOldTokenIfNeeded(String accessToken, TokenId tokenId, UserId userId) {
    Instant expirationTime = jwtPort.getExpirationTime(accessToken);
    if (expirationTime.isAfter(Instant.now())) {
      invalidateToken(tokenId, userId, expirationTime);
    }
  }

  private void invalidateToken(TokenId tokenId, UserId userId, Instant expiration) {
    persistencePort.addToBlacklist(tokenId, expiration);
    persistencePort.removeRefreshToken(tokenId, userId);
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

  private TokenValidationResult validateAndCheckBlacklist(String token) {
    TokenValidationResult validationResult = validateToken(token);
    if (persistencePort.isBlacklisted(validationResult.tokenId())) {
      throw new AuthServiceException(AuthServiceErrorCode.BLACKLISTED_TOKEN);
    }
    return validationResult;
  }
}