package com.logistic.common.internal.request;

import com.logistic.common.passport.model.Passport;
import java.util.List;

public record DeliveryClientRequest(
    List<Long> deliveryIds,
    Passport passport) {
}
