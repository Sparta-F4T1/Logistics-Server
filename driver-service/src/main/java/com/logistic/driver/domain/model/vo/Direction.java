package com.logistic.driver.domain.model.vo;

public record Direction(
    Long departCompanyId,
    Long arrivalCompanyId,
    Integer distance,
    Integer duration) {
}
