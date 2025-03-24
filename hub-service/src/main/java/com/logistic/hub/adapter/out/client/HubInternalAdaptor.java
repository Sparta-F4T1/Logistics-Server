package com.logistic.hub.adapter.out.client;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.response.GpsClientResponse;
import com.logistic.common.internal.response.UserClientResponse;
import com.logistic.hub.adapter.out.client.mapper.HubClientMapper;
import com.logistic.hub.application.port.in.command.RouteInfoCommand;
import com.logistic.hub.application.port.in.command.UserInfoCommand;
import com.logistic.hub.application.port.out.client.HubInternalPort;
import com.logistic.hub.domain.command.AddressCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class HubInternalAdaptor implements HubInternalPort {
  private final GpsFeignClient gpsFeignClient;
  private final UserFeignClient userFeignClient;
  private final HubClientMapper hubClientMapper;

  @Override
  public AddressCommand getAddressCommand(String roadAddress, String jibunAddress) {
    GpsClientResponse gps = gpsFeignClient.findGps(roadAddress);

    return hubClientMapper.toAddressCommand(gps);
    //return new AddressCommand(gps.road(), gps.jibun(), gps.latitude(), gps.longitude());
  }

  @Override
  public RouteInfoCommand getRouteInfo(String depart, String arrival) {
    GpsClientResponse distanceAndDuration = gpsFeignClient.findDistanceAndDuration(depart, arrival);
    return hubClientMapper.toRouteInfoCommand(distanceAndDuration);
    //return new RouteInfoCommand(distanceAndDuration.distance(), distanceAndDuration.duration());
  }

  @Override
  public List<UserInfoCommand> findUserList(List<String> userIds) {
    List<UserClientResponse> userList = userFeignClient.findUserList(userIds);

    return userList.stream().map(hubClientMapper::toUserInfoCommand).toList();
  }
}
