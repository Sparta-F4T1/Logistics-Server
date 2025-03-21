package com.logistic.user.adapter.out.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "passportAuditorAware")
public class JpaConfig {
  @Bean
  public AuditorAware<String> passportAuditorAware() {
    return new PassportAuditorAware();
  }
}