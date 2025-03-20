package com.logistic.product.adapter.in.external.web.request;

public record SearchProductRequest(
    Long companyId,
    String name) {
}
