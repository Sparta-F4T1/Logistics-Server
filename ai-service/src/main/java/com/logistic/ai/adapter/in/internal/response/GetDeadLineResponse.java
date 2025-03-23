package com.logistic.ai.adapter.in.internal.response;

import java.time.LocalDateTime;

public record GetDeadLineResponse(
    LocalDateTime deadLine
) {
}
