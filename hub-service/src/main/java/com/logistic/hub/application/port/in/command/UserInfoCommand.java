package com.logistic.hub.application.port.in.command;

public record UserInfoCommand(
    String userId,
    String role
) {
}
