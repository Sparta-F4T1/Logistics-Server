package com.logistic.common.internal.message;

import java.util.Map;

public record CancelOrderEvent(
    Map<Long, Integer> stockMap
) {
}
