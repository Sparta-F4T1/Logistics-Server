package com.logistic.common.internal.response;

import com.logistic.common.passport.model.Passport;

public record AuthClientResponse(
    Boolean successful,
    String userId,
    Passport passport,
    String message) {
}
