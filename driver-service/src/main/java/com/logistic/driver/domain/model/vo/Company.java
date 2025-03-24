package com.logistic.driver.domain.model.vo;

public record Company(
    Long companyId,
    String name,
    Double latitude,
    Double longitude
) {
}
