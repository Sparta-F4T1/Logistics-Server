package com.logistic.auth.application.port.in.command;

public record LoginCommand(String username, String password) {
}