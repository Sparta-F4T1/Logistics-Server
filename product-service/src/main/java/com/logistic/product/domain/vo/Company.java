package com.logistic.product.domain.vo;

import java.util.List;

public record Company(
    Long companyId,
    String companyName,
    Long hubId,
    List<String> userIds) {
}
