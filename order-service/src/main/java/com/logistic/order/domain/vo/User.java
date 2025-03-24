package com.logistic.order.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
  private String userId;
  private String userName;

  public static User create(String userId, String userName){
    return User.builder()
        .userId(userId)
        .userName(userName)
        .build();
  }
}
