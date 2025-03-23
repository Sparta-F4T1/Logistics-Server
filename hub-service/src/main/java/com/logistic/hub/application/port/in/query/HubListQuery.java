package com.logistic.hub.application.port.in.query;

import com.logistic.common.passport.model.Passport;
import java.util.List;

public record HubListQuery(
    List<Long> hubIds,
    Passport passport
) {
}
