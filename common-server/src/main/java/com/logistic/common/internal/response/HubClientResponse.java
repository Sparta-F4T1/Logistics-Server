package com.logistic.common.internal.response;

public record HubClientResponse(
    Long hubId,
    String hubType,
    String hubName,
    String road,
    String jibun,
    Double latitude,
    Double longitude) {
}
