package com.logistic.notification.adapter.in.web.response;

import lombok.Builder;

@Builder
public record SlackMessageResponse(
    String recipient,
    String text
) {
}