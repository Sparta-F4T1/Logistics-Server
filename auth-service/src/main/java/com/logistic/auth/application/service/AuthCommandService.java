package com.logistic.auth.application.service;

import com.logistic.auth.application.port.in.AuthCommandUseCase;
import com.logistic.auth.application.port.in.command.LoginCommand;
import com.logistic.auth.application.port.out.persistence.AuthPersistencePort;
import com.logistic.auth.application.port.out.support.jwt.AuthJwtPort;
import com.logistic.auth.application.port.out.support.uuid.IdGeneratorPort;
import com.logistic.auth.domain.TokenPair;
import com.logistic.auth.domain.User;
import com.logistic.auth.domain.exception.AuthServiceErrorCode;
import com.logistic.auth.domain.exception.AuthServiceException;
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
}