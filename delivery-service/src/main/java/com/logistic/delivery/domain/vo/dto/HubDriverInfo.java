package com.logistic.delivery.domain.vo.dto;

public record HubDriverInfo(
    String driverId,
    Long departHubId,
    Long arrivalHubId) {
}