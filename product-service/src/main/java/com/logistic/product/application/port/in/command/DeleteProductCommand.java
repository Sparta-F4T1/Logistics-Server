package com.logistic.product.application.port.in.command;

import com.logistic.common.passport.model.Passport;

public record DeleteProductCommand(
    Long productId,
    Long companyId,
    Passport passport) {
}
