package com.logistic.product.adapter.in.external.web.response;

public record CommandProductResponse(
    Long productId,
    String name,
    Long companyId,
    Integer quantity) {
}
