package com.logistic.auth.adapter.out.support.jwt.util;

import com.logistic.auth.adapter.out.support.jwt.config.JwtConfig;
import com.logistic.auth.domain.exception.AuthServiceErrorCode;
import com.logistic.auth.domain.exception.AuthServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Map;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator extends AbstractJwtSupport {
  private static final Map<Class<? extends Exception>, AuthServiceErrorCode> ERROR_MAP = Map.of(
      SecurityException.class, AuthServiceErrorCode.INVALID_SIGNATURE,
      SignatureException.class, AuthServiceErrorCode.INVALID_SIGNATURE,
      MalformedJwtException.class, AuthServiceErrorCode.MALFORMED_TOKEN,
      UnsupportedJwtException.class, AuthServiceErrorCode.UNSUPPORTED_TOKEN,
      IllegalArgumentException.class, AuthServiceErrorCode.EMPTY_CLAIMS
  );

  public JwtValidator(JwtConfig jwtConfig) {
    super(jwtConfig);
  }

  public Claims validateToken(@NonNull String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException e) {
      throw new AuthServiceException(AuthServiceErrorCode.EXPIRED_TOKEN, e);
    } catch (Exception e) {
      AuthServiceErrorCode error = ERROR_MAP.getOrDefault(e.getClass(), AuthServiceErrorCode.JWT_GENERAL_ERROR);
      throw new AuthServiceException(error, e);
    }
  }
}