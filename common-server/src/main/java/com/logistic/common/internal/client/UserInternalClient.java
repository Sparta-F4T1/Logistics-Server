package com.logistic.common.internal.client;

import com.logistic.common.internal.request.UserClientRequest;
import com.logistic.common.internal.response.UserClientResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserInternalClient {
  @GetMapping("/internal/v1/users/{userId}")
  UserClientResponse findUser(@PathVariable("userId") String userId, @RequestBody UserClientRequest request);

  @GetMapping("/internal/v1/users")
  List<UserClientResponse> findUserList(@RequestBody UserClientRequest request);
}
