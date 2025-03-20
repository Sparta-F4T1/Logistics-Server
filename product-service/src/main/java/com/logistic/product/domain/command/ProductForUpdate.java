package com.logistic.product.domain.command;

public record ProductForUpdate(
    String name,
    Integer quantity) {
}
