package com.logistic.product.application.port.in.command;

import com.logistic.common.passport.model.Passport;

public record UpdateProductCommand(
    Long productId,
    String name,
    Integer quantity,
    Passport passport) {
}
