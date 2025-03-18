package com.logistic.common.internal.response;

public record AiClientResponse(
    Long aiId,
    String request,
    String response) {
}
