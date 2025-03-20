package com.logistic.product.application.port.in.command;

import com.logistic.common.passport.model.Passport;

public record CreateProductCommand(
    String name,
    Integer quantity,
    Long companyId,
    Passport passport) {
}
