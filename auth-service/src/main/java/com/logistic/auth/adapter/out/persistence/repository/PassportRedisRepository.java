package com.logistic.auth.adapter.out.persistence.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistic.auth.domain.Passport;
import com.logistic.auth.domain.exception.AuthServiceErrorCode;
import com.logistic.auth.domain.exception.AuthServiceException;
import com.logistic.auth.domain.vo.UserId;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
@RequiredArgsConstructor
public class PassportRedisRepository {
  private static final String PASSPORT_PREFIX = "passport:";
  private static final String USER_PASSPORT_PREFIX = "user:passport:";

  private final StringRedisTemplate redisTemplate;
  private final ObjectMapper objectMapper;

  public void savePassport(Passport passport) {
    try {
      String passportKey = PASSPORT_PREFIX + passport.getSessionId();
      String userPassportKey = USER_PASSPORT_PREFIX + passport.getUserId().value();

      String passportJson = objectMapper.writeValueAsString(passport);

      long ttlMillis = calculateTtlMillis(passport.getExpiresAt());

      redisTemplate.opsForValue().set(passportKey, passportJson, ttlMillis, TimeUnit.MILLISECONDS);
      redisTemplate.opsForValue().set(userPassportKey, passport.getSessionId(), ttlMillis, TimeUnit.MILLISECONDS);

    } catch (JsonProcessingException e) {
      throw AuthServiceException.etc(AuthServiceErrorCode.PASSPORT_CACHE_FAILURE);
    }
  }

  public Passport findPassportByUserId(UserId userId) {
    String userPassportKey = USER_PASSPORT_PREFIX + userId.value();

    String sessionId = redisTemplate.opsForValue().get(userPassportKey);
    if (sessionId == null) {
      return null;
    }

    return findPassportBySessionId(sessionId);
  }

  public Passport findPassportBySessionId(String sessionId) {
    String passportKey = PASSPORT_PREFIX + sessionId;
    String passportJson = redisTemplate.opsForValue().get(passportKey);
    if (passportJson == null) {
      return null;
    }
    try {
      return objectMapper.readValue(passportJson, Passport.class);
    } catch (JsonProcessingException e) {
      throw AuthServiceException.etc(AuthServiceErrorCode.PASSPORT_JSON_PROCESSING_FAILURE);
    }
  }

  public void removePassport(UserId userId, String sessionId) {
    String passportKey = PASSPORT_PREFIX + sessionId;
    String userPassportKey = USER_PASSPORT_PREFIX + userId.value();

    redisTemplate.delete(passportKey);
    redisTemplate.delete(userPassportKey);
  }

  public void invalidateAllUserPassports(UserId userId) {
    String userPassportKey = USER_PASSPORT_PREFIX + userId.value();

    String sessionId = redisTemplate.opsForValue().get(userPassportKey);
    if (sessionId != null) {
      String passportKey = PASSPORT_PREFIX + sessionId;
      redisTemplate.delete(passportKey);
      redisTemplate.delete(userPassportKey);
    }
  }

  private long calculateTtlMillis(LocalDateTime expiresAt) {
    long expiresAtMillis = expiresAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    long nowMillis = System.currentTimeMillis();
    return Math.max(0, expiresAtMillis - nowMillis);
  }
}