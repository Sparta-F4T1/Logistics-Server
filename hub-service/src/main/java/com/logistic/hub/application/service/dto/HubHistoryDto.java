package com.logistic.hub.application.service.dto;

import com.logistic.hub.domain.HubType;

public record HubHistoryDto(
    Long hubId,
    HubType hubType,
    String hubName,
    String roadAddress,
    String jibunAddress
) {
}
