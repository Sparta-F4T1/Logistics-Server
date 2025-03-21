package com.logistic.hub.application.port.in.query;

public record RouteSearchQuery(
    int page,
    int size,
    String searchType,
    String search) {
}
