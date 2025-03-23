package com.logistic.common.internal.response;

import java.util.List;

public record HubClientResponse(
    Long hubId,
    String hubType,
    String hubName,
    String road,
    String jibun,
    Double latitude,
    Double longitude,
    List<String> userIds) {
}
