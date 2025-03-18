package com.logistic.order.adaptor.in.web.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record OrderProductCreateRequest(
    @NotEmpty(message = "품목은 필수입니다.")Long productId,
    @Min(1) int amount
) {
}
