package com.logistic.common.internal.response;

import java.util.List;

public record CompanyClientResponse(
    Long companyId,
    String companyName,
    String type,
    String road,
    String jibun,
    Double latitude,
    Double longitude,
    Long hubId,
    List<String> userIds) {
}
