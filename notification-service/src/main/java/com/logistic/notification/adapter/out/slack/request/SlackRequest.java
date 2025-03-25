package com.logistic.notification.adapter.out.slack.request;

public record SlackRequest(
    String userId,
    String text
) {
}
