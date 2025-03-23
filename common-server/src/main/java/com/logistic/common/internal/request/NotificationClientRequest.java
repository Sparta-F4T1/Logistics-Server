package com.logistic.common.internal.request;

public record NotificationClientRequest(
    String userId,
    String text) {
}
