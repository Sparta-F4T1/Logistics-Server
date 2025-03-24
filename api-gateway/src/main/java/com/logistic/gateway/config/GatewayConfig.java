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
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder.Builder;
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
    Builder routes = builder.routes();
    TokenExtractorFilter tokenExtractorFilter = tokenExtractorFilter();
    AuthenticationFilter authenticationFilter = jwtAuthFilter();
    AuthorizationFilter authorizationFilter = authorizationFilter();

    addAuthServiceRoute(routes, tokenExtractorFilter, authorizationFilter);

    addSecuredServiceRoute(routes, RouteId.USER, ServiceUri.USER,
        tokenExtractorFilter, authorizationFilter, ApiPath.Api.USERS_ALL);

    addSecuredServiceRoute(routes, RouteId.AI, ServiceUri.AI,
        tokenExtractorFilter, authorizationFilter, ApiPath.Api.USERS_ALL);

    addSecuredServiceRoute(routes, RouteId.COMPANY, ServiceUri.COMPANY,
        tokenExtractorFilter, authorizationFilter, ApiPath.Api.COMPANY_ALL);

    addSecuredServiceRoute(routes, RouteId.DELIVERY, ServiceUri.DELIVERY,
        tokenExtractorFilter, authorizationFilter, ApiPath.Api.DELIVERY_ALL);

    addSecuredServiceRoute(routes, RouteId.DRIVER, ServiceUri.DRIVER,
        tokenExtractorFilter, authorizationFilter, ApiPath.Api.DRIVER_ALL);

    addSecuredServiceRoute(routes, RouteId.GPS, ServiceUri.GPS,
        tokenExtractorFilter, authorizationFilter, ApiPath.Api.GPS_ALL);

    addSecuredServiceRoute(routes, RouteId.HUB, ServiceUri.HUB,
        tokenExtractorFilter, authorizationFilter,
        ApiPath.Api.HUB_ALL, ApiPath.Api.HUB_ROUTES_ALL);

    addSecuredServiceRoute(routes, RouteId.ORDER, ServiceUri.ORDER,
        tokenExtractorFilter, authorizationFilter, ApiPath.Api.ORDER_ALL);

    addSecuredServiceRoute(routes, RouteId.PRODUCT, ServiceUri.PRODUCT,
        tokenExtractorFilter, authorizationFilter, ApiPath.Api.PRODUCT_ALL);

    return routes.build();
  }

  private void addAuthServiceRoute(Builder routes, TokenExtractorFilter tokenExtractorFilter,
                                   AuthorizationFilter authorizationFilter) {

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
  }

  private void addSecuredServiceRoute(Builder routes, String routeId, String serviceUri,
                                      TokenExtractorFilter tokenExtractorFilter,
                                      AuthorizationFilter authorizationFilter,
                                      String... apiPaths) {

    routes.route(routeId, r -> r
        .path(apiPaths)
        .filters(f -> f
            .filter((exchange, chain) ->
                tokenExtractorFilter.filter(exchange, exch ->
                    authorizationFilter.filter(exch, chain))
            )
        )
        .uri(serviceUri)
    );
  }
}