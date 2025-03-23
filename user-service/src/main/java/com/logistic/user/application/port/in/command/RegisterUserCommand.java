package com.logistic.user.application.port.in.command;

public record RegisterUserCommand(
    String userId,
    String name,
    String password,
    String confirmedPassword,
    String slackAccount,
    Long roleId,
    String roleName,
    String currentUserId,
    String currentUserRole
) {
}