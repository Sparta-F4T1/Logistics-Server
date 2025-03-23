package com.logistic.user.application.port.in.query;

public record FindUserQuery(
    String userId,
    String currentUserId,
    String currentUserRole) {
}
