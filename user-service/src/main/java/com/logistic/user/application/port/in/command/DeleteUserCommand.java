package com.logistic.user.application.port.in.command;

public record DeleteUserCommand(
    String targetUserId,
    String currentUserId,
    String currentUserRole
) {
}
