package com.logistic.user.domain.command;

public record UserForUpdate(
    String password,
    String slackAccount
) {
  public static UserForUpdate of(String password, String slackAccount) {
    return new UserForUpdate(password, slackAccount);
  }
}