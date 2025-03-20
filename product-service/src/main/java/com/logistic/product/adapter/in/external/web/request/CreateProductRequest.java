package com.logistic.product.adapter.in.external.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProductRequest(
    @NotBlank(message = "상품명은 필수입니다.") String name,
    @NotNull(message = "재고는 필수입니다.") Integer quantity,
    @NotNull(message = "업체는 필수입니다.") Long companyId) {
}
