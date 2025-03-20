package com.logistic.product.domain.command;

public record ProductForCreate(
    String name,
    Integer quantity,
    Long companyId) {
}
