package com.logistic.user.adapter.in.internal;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.response.UserClientResponse;
import com.logistic.common.passport.annotation.WithPassport;
import com.logistic.common.passport.model.Passport;
import com.logistic.user.adapter.in.web.mapper.UserWebMapper;
import com.logistic.user.application.port.in.UserQueryUseCase;
import com.logistic.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/users")
public class UserServiceAdapter {
  private final UserQueryUseCase userQueryUseCase;
  private final UserWebMapper userWebMapper;

  @GetMapping("/{userId}")
  public UserClientResponse findUser(@PathVariable("userId") String userId,
                                     @WithPassport Passport passport) {
    final User user = userQueryUseCase.findUser(userWebMapper.toFindQuery(userId, passport));
    return new UserClientResponse(
        user.getUserId().value(),
        user.getSlackAccount().value(),
        user.getRole().toString(),
        user.getName().value(),
        user.getStatus().name());
  }

  @GetMapping
  public List<UserClientResponse> findUsers(@RequestParam List<String> userIds,
                                            @WithPassport Passport passport) {
    List<UserClientResponse> responses = new ArrayList<>();
    for (final String userId : userIds) {
      final User user = userQueryUseCase.findUser(userWebMapper.toFindQuery(userId, passport));
      responses.add(new UserClientResponse(
          user.getUserId().value(),
          user.getSlackAccount().value(),
          user.getRole().toString(),
          user.getName().value(),
          user.getStatus().name()));
    }
    return responses;
  }

}
