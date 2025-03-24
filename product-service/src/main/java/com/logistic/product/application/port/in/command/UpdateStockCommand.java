package com.logistic.product.application.port.in.command;

import com.logistic.common.passport.model.Passport;
import java.util.Map;

public record UpdateStockCommand(
    Map<Long, Integer> stockMap,
    Passport passport) {
}
