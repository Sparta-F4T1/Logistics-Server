package com.logistic.hub.adaptor.in.web.response;

import com.logistic.hub.domain.HubType;

public record HubHistoryResponse(
    Long hubId,
    HubType hubType,
    String hubName,
    String roadAddress,
    String jibunAddress
) {
}