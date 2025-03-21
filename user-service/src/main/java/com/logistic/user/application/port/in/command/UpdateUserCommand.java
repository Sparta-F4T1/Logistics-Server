package com.logistic.user.application.port.in.command;

public record UpdateUserCommand(
    String targetUserId,
    String password,
    String confirmedPassword,
    String slackAccount,
    String currentUserId,
    String currentUserRole
) {
}