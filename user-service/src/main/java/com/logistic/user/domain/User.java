package com.logistic.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class User {

  private String username;
  private String password;
  private UserRole role;
  private String slackAccount;

  @Builder
  private User(String username, String password, UserRole role, String slackAccount) {
    this.username = username;
    this.password = password;
    this.role = role;
    this.slackAccount = slackAccount;
  }

}
