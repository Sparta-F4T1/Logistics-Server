package com.logistic.user.adapter.in.web.response;

public record FindUserResponse(
    String userId,
    String name,
    String slackAccount,
    Long roleId,
    String roleName,
    String status
) {
}