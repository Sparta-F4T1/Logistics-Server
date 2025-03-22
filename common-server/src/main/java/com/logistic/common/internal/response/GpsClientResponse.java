package com.logistic.common.internal.response;

public record GpsClientResponse(
    Long gpsId,
    String road,
    String jibun,
    Double latitude,
    Double longitude,
    Integer duration,
    Integer distance
) {
}
