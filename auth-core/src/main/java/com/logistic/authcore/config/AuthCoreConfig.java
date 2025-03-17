package com.logistic.authcore.config;

import com.logistic.authcore.repository.AuthTokenRepository;
import com.logistic.authcore.repository.ReactiveTokenVerificationRepository;
import com.logistic.authcore.repository.SyncAuthTokenRepository;
import com.logistic.authcore.repository.SyncTokenVerificationRepository;
import com.logistic.authcore.repository.TokenVerificationRepository;
import com.logistic.authcore.service.AuthTokenService;
import com.logistic.authcore.service.AuthTokenServiceImpl;
import com.logistic.authcore.service.ReactiveTokenVerificationService;
import com.logistic.authcore.service.TokenVerificationService;
import com.logistic.authcore.service.TokenVerificationServiceImpl;
import com.logistic.authcore.util.JwtProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class AuthCoreConfig {

  @Bean
  @ConditionalOnMissingBean
  public JwtProvider jwtProvider(JwtProperties jwtProperties) {
    return new JwtProvider(jwtProperties);
  }

  @Configuration
  @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
  static class ServletAuthConfig {

    @Bean
    @ConditionalOnMissingBean(AuthTokenRepository.class)
    public SyncAuthTokenRepository syncAuthTokenRepository(StringRedisTemplate redisTemplate) {
      return new SyncAuthTokenRepository(redisTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(TokenVerificationRepository.class)
    public SyncTokenVerificationRepository syncTokenVerificationRepository(StringRedisTemplate redisTemplate) {
      return new SyncTokenVerificationRepository(redisTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(AuthTokenService.class)
    public AuthTokenService authTokenService(
        JwtProperties jwtProperties,
        JwtProvider jwtProvider,
        AuthTokenRepository authTokenRepository,
        TokenVerificationRepository tokenVerificationRepository) {
      return new AuthTokenServiceImpl(jwtProperties, jwtProvider, authTokenRepository, tokenVerificationRepository);
    }

    @Bean
    @ConditionalOnMissingBean(TokenVerificationService.class)
    public TokenVerificationService tokenVerificationService(
        JwtProperties jwtProperties,
        JwtProvider jwtProvider,
        TokenVerificationRepository tokenVerificationRepository) {
      return new TokenVerificationServiceImpl(jwtProperties, jwtProvider, tokenVerificationRepository);
    }
  }

  @Configuration
  @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
  static class ReactiveAuthConfig {

    @Bean
    @ConditionalOnMissingBean(ReactiveTokenVerificationRepository.class)
    public ReactiveTokenVerificationRepository reactiveTokenVerificationRepository(
        ReactiveRedisTemplate<String, String> reactiveRedisTemplate) {
      return new ReactiveTokenVerificationRepository(reactiveRedisTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public ReactiveTokenVerificationService reactiveTokenVerificationService(
        JwtProperties jwtProperties,
        JwtProvider jwtProvider,
        ReactiveTokenVerificationRepository reactiveTokenVerificationRepository) {
      return new ReactiveTokenVerificationService(jwtProperties, jwtProvider, reactiveTokenVerificationRepository);
    }
  }
}