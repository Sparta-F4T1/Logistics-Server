package com.logistic.hub.adapter.out.client;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.request.GpsClientRequest;
import com.logistic.common.internal.response.GpsClientResponse;
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
    GpsClientRequest request = new GpsClientRequest(null, roadAddress, null, null, null);
    GpsClientResponse gps = gpsFeignClient.findGps(1L, roadAddress);

    return new AddressCommand(gps.road(), gps.jibun(), gps.latitude(), gps.longitude());
  }

  @Override
  public RouteInfoCommand getRouteInfo(String depart, String arrival) {
    GpsClientResponse distanceAndDuration = gpsFeignClient.findDistanceAndDuration(depart, arrival);
    return new RouteInfoCommand(distanceAndDuration.distance(), distanceAndDuration.duration());
  }
}
