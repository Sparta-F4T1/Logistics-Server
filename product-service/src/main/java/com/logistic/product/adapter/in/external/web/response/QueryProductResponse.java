package com.logistic.product.adapter.in.external.web.response;

public record QueryProductResponse(
    Long productId,
    String productName,
    Integer quantity,
    Long companyId,
    String companyName) {
}
