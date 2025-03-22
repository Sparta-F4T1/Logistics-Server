package com.logistic.driver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableFeignClients
@SpringBootApplication
@Import(com.logistic.common.passport.PassportWebConfig.class)
public class DriverApplication {
  public static void main(String[] args) {
    SpringApplication.run(DriverApplication.class, args);
  }
}
