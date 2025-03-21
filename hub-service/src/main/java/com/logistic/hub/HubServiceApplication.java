package com.logistic.hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableFeignClients
@EnableJpaAuditing
@Import(com.logistic.common.passport.PassportWebConfig.class)
@SpringBootApplication
public class HubServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(HubServiceApplication.class, args);
  }

}
