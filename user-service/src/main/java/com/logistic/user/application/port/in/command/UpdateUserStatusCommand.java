package com.logistic.user.application.port.in.command;

public record UpdateUserStatusCommand(
    String targetUserId,
    String status,
    String currentUserId,
    String currentUserRole
) {
}