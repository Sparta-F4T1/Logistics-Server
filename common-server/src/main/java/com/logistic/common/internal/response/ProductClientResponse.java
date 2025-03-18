package com.logistic.common.internal.response;

public record ProductClientResponse(
    Long productId,
    String name,
    Integer quantity,
    Long hubId) {
}
