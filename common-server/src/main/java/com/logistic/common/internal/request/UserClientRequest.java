package com.logistic.common.internal.request;

import com.logistic.common.passport.model.Passport;
import java.util.List;

public record UserClientRequest(
    List<String> userIds,
    Passport passport) {
}
