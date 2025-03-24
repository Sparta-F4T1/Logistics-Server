package com.logistic.product.domain.command;

import com.logistic.product.domain.vo.Company;

public record ProductForCreate(
    String name,
    Integer quantity,
    Company company) {
}
