package com.logistic.common.internal.request;

import java.util.Map;

public record ProductClientRequest(
    Map<Long, Integer> stockMap) {
}
