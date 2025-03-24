package com.logistic.product.domain.vo;

import java.util.List;

public record Hub(
    Long hubId,
    List<String> userIds) {
}
