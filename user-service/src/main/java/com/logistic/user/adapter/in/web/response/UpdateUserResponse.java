package com.logistic.user.adapter.in.web.response;

public record UpdateUserResponse(
    String userId,
    String slackAccount
) {
}