package com.logistic.user.adapter.in.web.request;

public record SearchUserRequest(
    String role,
    String status
) {
}