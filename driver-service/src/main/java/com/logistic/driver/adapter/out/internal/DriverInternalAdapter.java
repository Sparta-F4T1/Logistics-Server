package com.logistic.driver.adapter.out.internal;

import com.logistic.common.internal.request.HubClientRequest;
import com.logistic.driver.adapter.out.internal.client.HubFeignClient;
import com.logistic.driver.adapter.out.internal.client.UserFeignClient;
import com.logistic.driver.adapter.out.internal.mapper.DriverClientMapper;
import com.logistic.driver.application.port.out.DriverInternalPort;
import com.logistic.driver.domain.exception.CustomNotFoundException.HubNotFoundException;
import com.logistic.driver.domain.exception.CustomNotFoundException.UserNotFoundException;
import com.logistic.driver.domain.model.vo.Hub;
import com.logistic.driver.domain.model.vo.User;
import feign.FeignException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DriverInternalAdapter implements DriverInternalPort {
  private final DriverClientMapper mapper;
  private final HubFeignClient hubFeignClient;
  private final UserFeignClient userFeignClient;

  @Override
  public Hub findHub(final Long hubId) {
    try {
      return mapper.toHub(hubFeignClient.findHub(hubId, null));
    } catch (FeignException e) {
      throw new HubNotFoundException();
    }
  }

  @Override
  public List<Hub> findHubList(final List<Long> hubIds) {
    final HubClientRequest request = new HubClientRequest(hubIds, null);
    try {
      return hubFeignClient.findHubList(request).stream().map(mapper::toHub).toList();
    } catch (FeignException e) {
      throw new HubNotFoundException();
    }
  }

  @Override
  public User findUser(final String userId) {
    try {
      return mapper.toUser(userFeignClient.findUser(userId, null));
    } catch (FeignException e) {
      throw new UserNotFoundException();
    }
  }
}
