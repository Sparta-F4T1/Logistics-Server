package com.logistic.gateway.filter.auth;

import static com.logistic.gateway.constants.CommonFilterConstants.ERROR_MISSING_TOKEN;
import static com.logistic.gateway.constants.CommonFilterConstants.TOKEN_ATTR;

import java.nio.charset.StandardCharsets;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class TokenExtractorFilter implements GatewayFilter {
  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String BEARER_PREFIX = "Bearer ";

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String token = extractTokenFromHeader(exchange.getRequest());
    if (token == null) {
      return handleMissingToken(exchange, ERROR_MISSING_TOKEN);
    }

    exchange.getAttributes().put(TOKEN_ATTR, token);
    return chain.filter(exchange);
  }

  private String extractTokenFromHeader(ServerHttpRequest request) {
    return request.getHeaders()
        .getOrEmpty(AUTHORIZATION_HEADER)
        .stream()
        .filter(header -> header.startsWith(BEARER_PREFIX))
        .findFirst()
        .map(header -> header.substring(BEARER_PREFIX.length()))
        .orElse(null);
  }

  private Mono<Void> handleMissingToken(ServerWebExchange exchange, String message) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(HttpStatus.UNAUTHORIZED);

    byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
    DataBuffer buffer = response.bufferFactory().wrap(bytes);
    return response.writeWith(Mono.just(buffer));
  }
}