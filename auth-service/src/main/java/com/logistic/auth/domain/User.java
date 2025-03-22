package com.logistic.auth.domain;

import com.logistic.auth.domain.vo.Password;
import com.logistic.auth.domain.vo.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
  private UserId userId;
  private Password password;

  public static User mock() {
    return User.builder()
        .userId(UserId.of("userId"))
        .password(Password.mock())
        .build();
  }
}