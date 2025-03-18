package com.logistic.common.internal.request;

public record AuthClientRequest(
    String token,
    String uri,
    String method) {
}
