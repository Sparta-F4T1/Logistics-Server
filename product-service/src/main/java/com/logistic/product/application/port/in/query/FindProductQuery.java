package com.logistic.product.application.port.in.query;

import com.logistic.common.passport.model.Passport;

public record FindProductQuery(
    Long productId,
    Passport passport) {
}
