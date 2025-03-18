package com.logistic.common.internal.request;

import com.logistic.common.passport.model.Passport;
import java.util.List;

public record RouteClientRequest(
    List<Long> routeIds,
    Long departHubId,
    Long arrivalHubId,
    Passport passport) {
}
