package com.logistic.driver.adapter.out.internal;

import com.logistic.common.internal.request.HubClientRequest;
import com.logistic.common.passport.model.Passport;
import com.logistic.driver.adapter.out.internal.client.HubFeignClient;
import com.logistic.driver.adapter.out.internal.mapper.DriverClientMapper;
import com.logistic.driver.application.port.out.DriverInternalPort;
import com.logistic.driver.application.service.dto.HubInfo;
import com.logistic.driver.domain.exception.DomainException.HubNotFoundException;
import feign.FeignException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DriverInternalAdapter implements DriverInternalPort {
  private final DriverClientMapper mapper;
  private final HubFeignClient hubFeignClient;

  @Override
  public HubInfo findHub(final Long hubId, final Passport passport) {
    final HubClientRequest request = new HubClientRequest(null, passport);
    try {
      return mapper.toHubInfo(hubFeignClient.findHub(hubId, request));
    } catch (FeignException e) {
      throw new HubNotFoundException();
    }
  }

  @Override
  public List<HubInfo> findHubList(final List<Long> hubIds, final Passport passport) {
    final HubClientRequest request = new HubClientRequest(hubIds, passport);
    try {
      return hubFeignClient.findHubList(request).stream().map(mapper::toHubInfo).toList();
    } catch (FeignException e) {
      throw new HubNotFoundException();
    }
  }
}
