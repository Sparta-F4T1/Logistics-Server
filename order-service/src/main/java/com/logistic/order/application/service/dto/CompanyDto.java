package com.logistic.order.application.service.dto;

import java.util.List;

public record CompanyDto(
    Long companyId,
    String companyName,
    String type,
    Long hubId,
    List<String> userIds
) {
}
