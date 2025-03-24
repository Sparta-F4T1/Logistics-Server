package com.logistic.order.application.service.dto;

public record UserDto(
    String userId,
    String userName,
    String slackEmail
) {
}
