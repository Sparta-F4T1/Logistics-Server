package com.logistic.gateway.client;

import com.logistic.common.internal.request.AuthClientRequest;
import com.logistic.common.internal.response.AuthClientResponse;
import com.logistic.common.response.ApiResponse;
import com.logistic.gateway.constants.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthServiceClient {
  private final String authServiceUrl = "http://AUTH-SERVICE";
  private final WebClient.Builder webClientBuilder;

  public AuthServiceClient(WebClient.Builder webClientBuilder) {
    this.webClientBuilder = webClientBuilder;
  }

  public Mono<AuthClientResponse> verifyToken(String token, String uri, String method) {
    log.debug("토큰 검증 요청");

    return webClientBuilder.build()
        .post()
        .uri(authServiceUrl + AppConstants.ApiPath.Internal.AUTH_VERIFY_TOKEN)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new AuthClientRequest(token, uri, method))
        .retrieve()
        .bodyToMono(AuthClientResponse.class)
        .doOnError(e -> log.error("토큰 검증 오류: {}", e.getMessage()))
        .onErrorResume(e -> Mono.just(new AuthClientResponse(false, null, null, e.getMessage())));
  }

  public Mono<AuthClientResponse> authorize(String token, String uri, String method) {
    log.debug("인가 요청: uri={}, method={}", uri, method);

    return webClientBuilder.build()
        .post()
        .uri(authServiceUrl + AppConstants.ApiPath.Internal.AUTH_VALIDATE_ACCESS)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new AuthClientRequest(token, uri, method))
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<ApiResponse<AuthClientResponse>>() {
        })
        .map(ApiResponse::data)
        .doOnNext(response -> log.debug("인가 응답: {}", response))
        .doOnError(e -> log.error("인가 처리 오류: {}", e.getMessage()))
        .onErrorResume(e -> Mono.just(new AuthClientResponse(false, null, null, e.getMessage())));
  }
}