package com.logistic.auth.application.port.in.command;

public record IssuePassportCommand(String token, String uri, String method) {
}