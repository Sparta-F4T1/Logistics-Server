package com.logistic.hub.adaptor.out.client;

import com.logistic.common.annotation.Adapter;
import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.application.port.in.command.RouteInfoCommand;
import com.logistic.hub.application.port.out.client.GpsInternalPort;
import com.logistic.hub.domain.command.AddressCommand;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class GpsInternalAdaptor implements GpsInternalPort {
  private final GpsFeignClient gpsFeignClient;

  @Override
  public AddressCommand getAddressCommand(String roadAddress, String jibunAddress) {
    //gpsFeignClient.findGps(); 위도, 경도 찾기
    return new AddressCommand(roadAddress, jibunAddress, 300.0, 37.0); //임시
  }

  @Override
  public RouteInfoCommand getRouteInfo(RouteCreateCommand routeCommand) {
    //gpsFeignClient.findPath();  거리, 시간 찾기
    return new RouteInfoCommand(3000, 10000); // 임시
  }
}
