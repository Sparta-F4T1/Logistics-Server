package com.logistic.hub.application.port.in.query;

import java.util.List;

public record HubListQuery(
    List<Long> hubIds
) {
}
