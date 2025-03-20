package com.logistic.product.adapter.in.external.web.response;

public record FindProductResponse(
    Long productId,
    String name,
    Long companyId,
    Integer quantity) {
}
