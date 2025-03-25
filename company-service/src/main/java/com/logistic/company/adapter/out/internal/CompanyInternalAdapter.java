package com.logistic.company.adapter.out.internal;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.response.GpsClientResponse;
import com.logistic.common.internal.response.HubClientResponse;
import com.logistic.common.internal.response.UserClientResponse;
import com.logistic.company.adapter.out.internal.client.GpsFeignClient;
import com.logistic.company.adapter.out.internal.client.HubFeignClient;
import com.logistic.company.adapter.out.internal.client.UserFeignClient;
import com.logistic.company.adapter.out.internal.mapper.CompanyClientMapper;
import com.logistic.company.application.port.out.CompanyInternalPort;
import com.logistic.company.domain.model.vo.Gps;
import com.logistic.company.domain.model.vo.Hub;
import com.logistic.company.domain.model.vo.User;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class CompanyInternalAdapter implements CompanyInternalPort {
  private final GpsFeignClient gpsFeignClient;
  private final HubFeignClient hubFeignClient;
  private final UserFeignClient userFeignClient;
  private final CompanyClientMapper mapper;

  @Override
  public Gps findGps(final String road) {
    final GpsClientResponse response = gpsFeignClient.findGps(road);
    return mapper.toGps(response);
  }

  @Override
  public Hub findHub(final Long hubId) {
    final HubClientResponse response = hubFeignClient.findHub(hubId);
    return mapper.toHub(response);
  }

  @Override
  public List<User> findUserList(final List<String> userIds) {
    final List<UserClientResponse> response = userFeignClient.findUserList(userIds);
    return response.stream().map(mapper::toUser).toList();
  }
}
