package com.logistic.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistic.common.passport.util.PassportUtils;
import com.logistic.gateway.client.AuthServiceClient;
import com.logistic.gateway.constants.AppConstants.ApiPath;
import com.logistic.gateway.constants.AppConstants.RouteId;
import com.logistic.gateway.constants.AppConstants.ServiceUri;
import com.logistic.gateway.filter.auth.AuthenticationFilter;
import com.logistic.gateway.filter.auth.AuthorizationFilter;
import com.logistic.gateway.filter.auth.TokenExtractorFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

  private final ObjectMapper objectMapper;
  private final AuthServiceClient authServiceClient;

  @Bean
  public PassportUtils passportUtils() {
    return new PassportUtils(objectMapper);
  }

  @Bean
  public AuthenticationFilter jwtAuthFilter() {
    return new AuthenticationFilter(authServiceClient);
  }

  @Bean
  public AuthorizationFilter authorizationFilter() {
    return new AuthorizationFilter(authServiceClient, passportUtils());
  }

  @Bean
  public TokenExtractorFilter tokenExtractorFilter() {
    return new TokenExtractorFilter();
  }

  @Bean
  public RouteLocator routeLocator(RouteLocatorBuilder builder) {
    RouteLocatorBuilder.Builder routes = builder.routes();
    TokenExtractorFilter tokenExtractorFilter = tokenExtractorFilter();
    AuthenticationFilter authenticationFilter = jwtAuthFilter();
    AuthorizationFilter authorizationFilter = authorizationFilter();

    routes.route(RouteId.AUTH, r -> r
        .path(ApiPath.Api.AUTH_ALL)
        .filters(f -> f
            .filter((exchange, chain) -> {
              String path = exchange.getRequest().getURI().getPath();

              if (path.equals(ApiPath.Api.AUTH_LOGIN) ||
                  path.equals(ApiPath.Api.AUTH_REFRESH)) {
                return chain.filter(exchange);
              }

              if (path.equals(ApiPath.Api.AUTH_LOGOUT)) {
                return tokenExtractorFilter.filter(exchange, chain);
              }

              return tokenExtractorFilter.filter(exchange, exch ->
                  authorizationFilter.filter(exch, chain));
            })
        )
        .uri(ServiceUri.AUTH)
    );

    routes.route(RouteId.USER, r -> r
        .path(ApiPath.Api.USERS_ALL)
        .filters(f -> f
            .filter((exchange, chain) ->
                tokenExtractorFilter.filter(exchange, exch ->
                    authorizationFilter.filter(exch, chain))
            )
        )
        .uri(ServiceUri.USER)
    );
    return routes.build();
  }
}