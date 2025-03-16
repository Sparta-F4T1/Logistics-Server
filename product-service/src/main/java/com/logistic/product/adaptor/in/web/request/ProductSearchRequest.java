package com.logistic.product.adaptor.in.web.request;

import jakarta.validation.constraints.NotNull;

public record ProductSearchRequest(
    @NotNull Long companyId,
    String query) {
}
