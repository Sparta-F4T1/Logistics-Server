package com.logistic.product.application.port.in.command;

import com.logistic.common.passport.model.Passport;

public record AddStockCommand(
    Long productId,
    Integer quantity,
    Passport passport) {
}
