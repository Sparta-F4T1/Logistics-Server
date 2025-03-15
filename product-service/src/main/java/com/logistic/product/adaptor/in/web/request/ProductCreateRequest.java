package com.logistic.product.adaptor.in.web.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProductCreateRequest(
    @NotEmpty(message = "상품명은 필수입니다.") String name,
    @NotNull(message = "재고는 필수입니다.") Integer stock,
    @NotNull(message = "업체는 필수입니다.") Long companyId) {
}
