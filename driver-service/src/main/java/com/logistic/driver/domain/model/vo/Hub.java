package com.logistic.driver.domain.model.vo;

import java.util.List;

public record Hub(
    Long hubId,
    String hubType,
    String hubName,
    String road,
    String jibun,
    Double latitude,
    Double longitude,
    List<String> userIds) {
}
