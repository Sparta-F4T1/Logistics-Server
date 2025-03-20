package com.logistic.product.application.port.in.query;

import com.logistic.common.passport.model.Passport;
import java.util.List;

public record ListProductQuery(
    List<Long> productIds,
    Passport passport) {
}
