package com.logistic.common.internal.response;

public record CompanyClientResponse(
    Long companyId,
    String name,
    String type,
    String road,
    String jibun,
    Double latitude,
    Double longitude,
    Long hubId) {
}
