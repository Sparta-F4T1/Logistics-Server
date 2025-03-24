package com.logistic.order.application.service.dto;

import java.util.List;

public record HubDto(
    Long hubId,
    String hubName,
    List<String> userIds
) {
}
