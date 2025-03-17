package com.logistic.product.adaptor.in.web.response;

public record ProductResponse(Long productId, String name, Long companyId, Integer quantity, Boolean isDeleted) {
}
