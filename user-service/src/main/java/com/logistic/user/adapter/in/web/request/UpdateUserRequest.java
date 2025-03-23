package com.logistic.user.adapter.in.web.request;

public record UpdateUserRequest(
    String password,
    String confirmedPassword,
    String slackAccount) {
}