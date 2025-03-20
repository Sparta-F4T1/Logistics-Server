package com.logistic.gateway.filter.logging;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Log4j2(topic = "[Gateway Log]")
public class GatewayLoggingFilter implements GlobalFilter {
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String path = exchange.getRequest().getURI().getPath();
    log.info("요청 수신 - path: {}, method: {}", path, exchange.getRequest().getMethod());

    return chain.filter(exchange).doOnSuccess(aVoid ->
        log.info("요청 전달 완료 - path: {}", path)
    );
  }
}
