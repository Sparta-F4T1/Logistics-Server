package com.logistic.hub.application.port.in.query;

public record HubSearchQuery(int page, int size, String searchType, String search) {
}
