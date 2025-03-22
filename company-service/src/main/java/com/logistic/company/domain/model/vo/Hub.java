package com.logistic.company.domain.model.vo;

import java.util.List;

public record Hub(
    Long hubId,
    String hubType,
    String hubName,
    String hubRoad,
    String hubJibun,
    Double hubLatitude,
    Double hubLongitude,
    List<String> userIds) {
}
