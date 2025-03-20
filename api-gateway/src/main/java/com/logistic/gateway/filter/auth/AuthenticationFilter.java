package com.logistic.gateway.filter.auth;

import static com.logistic.gateway.constants.AppConstants.Filter.ERROR_MISSING_TOKEN;
import static com.logistic.gateway.constants.AppConstants.Filter.TOKEN_ATTR;

import com.logistic.gateway.client.AuthServiceClient;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter implements GatewayFilter {
  private static final String AUTHENTICATION_ERROR = "인증에 실패했습니다.";
  private final AuthServiceClient authServiceClient;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    log.debug("Authentication Filter 실행: {}", request.getPath());

    String token = (String) exchange.getAttributes().get(TOKEN_ATTR);
    if (token == null) {
      handleUnauthorized(exchange, ERROR_MISSING_TOKEN);
    }

    return authServiceClient.verifyToken(token, request.getPath().value(), request.getMethod().name())
        .flatMap(response -> {
          if (!response.getSuccessful()) {
            return handleUnauthorized(exchange, AUTHENTICATION_ERROR)
                .then(Mono.empty());
          }
          return chain.filter(exchange);
        });
  }

  private Mono<Void> handleUnauthorized(ServerWebExchange exchange, String message) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(HttpStatus.UNAUTHORIZED);

    DataBuffer buffer = response.bufferFactory().wrap(message.getBytes(StandardCharsets.UTF_8));
    return response.writeWith(Mono.just(buffer));
  }
}