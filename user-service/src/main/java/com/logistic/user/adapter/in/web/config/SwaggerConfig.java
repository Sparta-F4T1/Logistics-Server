package com.logistic.user.adapter.in.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(info());
  }

  private Info info() {
    return new Info()
        .title("[Logistics] User-service API")
        .description("User-service API 명세서")
        .version("1.0");
  }
}
