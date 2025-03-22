package com.logistic.auth.adapter.out.support.jwt.util;

import com.logistic.auth.adapter.out.support.jwt.config.JwtConfig;
import com.logistic.auth.domain.vo.TokenType;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider extends AbstractJwtSupport {
  protected static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

  public JwtProvider(JwtConfig jwtConfig) {
    super(jwtConfig);
  }

  public JwtBuilder createToken(@NonNull String subject, long expirationMs) {
    Date now = new Date();
    Date tokenExpiry = Date.from(Instant.now().plus(Duration.ofMillis(expirationMs)));

    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(now)
        .setExpiration(tokenExpiry)
        .signWith(key, SIGNATURE_ALGORITHM);
  }

  public String createAccessToken(@NonNull String subject, String jti) {
    return this.createToken(subject, getAccessExpirationMs()).setId(jti).compact();
  }

  public String createRefreshToken(@NonNull String subject, String jti) {
    return this.createToken(subject, getRefreshTokenExpirationMs()).setId(jti).compact();
  }

  private long getAccessExpirationMs() {
    return this.getAccessExpirationMs(TokenType.ACCESS);
  }

  private long getRefreshTokenExpirationMs() {
    return this.getAccessExpirationMs(TokenType.REFRESH);
  }

  private long getAccessExpirationMs(TokenType type) {
    return type.getExpiration().toMillis();
  }
}