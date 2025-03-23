package com.logistic.common.internal.response;

public record GpsClientResponse(
    String road,
    String jibun,
    Double latitude,
    Double longitude,
    Integer duration,
    Integer distance) {
}
