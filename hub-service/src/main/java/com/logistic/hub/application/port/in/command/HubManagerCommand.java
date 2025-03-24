package com.logistic.hub.application.port.in.command;

import com.logistic.common.passport.model.Passport;
import java.util.List;

public record HubManagerCommand(
    Long hubId,
    List<String> userIds,
    Passport passport
) {
}
