package com.logistic.user.adaptor.out.persistence;

import com.logistic.user.domain.User;

public class UserMapper {
  public static User toUserEntity(UserEntity userEntity) {
    return User.builder()
        .username(userEntity.getUserId())
        .password(userEntity.getPassword())
        .role(userEntity.getRole())
        .slackAccount(userEntity.getSlackAccount())
        .build();
  }

  public static UserEntity mapToUserEntity(User user) {
    return new UserEntity();
  }

}
