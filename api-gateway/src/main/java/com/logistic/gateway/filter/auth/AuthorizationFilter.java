package com.logistic.gateway.filter.auth;

import static com.logistic.common.passport.constant.PassportConstant.PASSPORT_HEADER;
import static com.logistic.gateway.constants.AppConstants.Filter.ERROR_MISSING_TOKEN;
import static com.logistic.gateway.constants.AppConstants.Filter.TOKEN_ATTR;

import com.logistic.common.passport.model.Passport;
import com.logistic.common.passport.util.PassportUtils;
import com.logistic.gateway.client.AuthServiceClient;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Log4j2(topic = "[Authorization]")
public class AuthorizationFilter implements GatewayFilter {
  private static final String FORBIDDEN_ERROR = "접근이 허용되지 않습니다";
  private static final String SERIALIZATION_ERROR = "Passport 직렬화 또는 Encoding 작업에 오류가 발생했습니다";

  private final AuthServiceClient authServiceClient;
  private final PassportUtils passportUtils;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    log.debug("Authorization Filter 실행: {}", request.getPath());

    String token = (String) exchange.getAttributes().get(TOKEN_ATTR);
    if (token == null) {
      handleForbidden(exchange, ERROR_MISSING_TOKEN);
    }
    return authServiceClient.authorize(token, request.getPath().value(), request.getMethod().name())
        .flatMap(response -> {
          if (!response.getSuccessful()) {
            return handleForbidden(exchange, FORBIDDEN_ERROR)
                .then(Mono.empty());
          }

          if (response.getPassport() == null) {
            return chain.filter(exchange);
          }

          return serializePassport(response.getPassport())
              .onErrorResume(e -> {
                log.error("Passport 직렬화 실패, Passport 없이 진행: {}", e.getMessage());
                return Mono.empty();
              })
              .flatMap(serializedPassport -> addPassportToRequest(exchange, chain, serializedPassport))
              .switchIfEmpty(chain.filter(exchange));
        });

  }

  private Mono<String> serializePassport(Passport passport) {
    try {
      String encoded = passportUtils.encode(passport);
      return Mono.just(encoded);
    } catch (Exception e) {
      log.error("Passport 직렬화 오류: {}", e.getMessage(), e);
      return Mono.error(new RuntimeException(SERIALIZATION_ERROR, e));
    }
  }

  private Mono<Void> addPassportToRequest(ServerWebExchange exchange, GatewayFilterChain chain, String passport) {
    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
        .header(PASSPORT_HEADER, passport)
        .build();

    return chain.filter(exchange.mutate().request(mutatedRequest).build());
  }


  private Mono<Void> handleForbidden(ServerWebExchange exchange, String message) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(HttpStatus.FORBIDDEN);

    DataBuffer buffer = response.bufferFactory().wrap(message.getBytes(StandardCharsets.UTF_8));
    return response.writeWith(Mono.just(buffer));
  }
}