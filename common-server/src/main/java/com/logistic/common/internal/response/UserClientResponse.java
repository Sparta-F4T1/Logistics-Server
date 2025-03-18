package com.logistic.common.internal.response;

public record UserClientResponse(
    String userId,
    String slackAccount,
    String name,
    String status) {
}
