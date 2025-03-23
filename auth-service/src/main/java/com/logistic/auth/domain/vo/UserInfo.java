package com.logistic.auth.domain.vo;

import com.logistic.auth.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfo {
  private UserId userId;
  private String role;

  public static UserInfo of(User user) {
    return UserInfo.builder()
        .userId(user.getUserId())
        .role(user.getRole().getName().getValue())
        .build();
  }

  public static UserInfo of(String userId, String role) {
    return UserInfo.builder()
        .userId(UserId.of(userId))
        .role(role)
        .build();
  }
}