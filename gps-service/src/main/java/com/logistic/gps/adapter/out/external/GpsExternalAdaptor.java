package com.logistic.gps.adapter.out.external;

import com.logistic.common.annotation.Adapter;
import com.logistic.gps.adapter.out.external.response.NaverDirectionResponse;
import com.logistic.gps.adapter.out.external.response.NaverGeoResponse;
import com.logistic.gps.application.port.in.command.GpsDistanceCommand;
import com.logistic.gps.application.port.out.GpsExternalPort;
import com.logistic.gps.domain.Direction;
import com.logistic.gps.domain.Gps;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Adapter
@RequiredArgsConstructor
public class GpsExternalAdaptor implements GpsExternalPort {
  private final NaverFeignClient naverFeignClient;

  @Value("${naver.api.clientId}")
  private String clientId;

  @Value("${naver.api.clientSecret}")
  private String clientSecret;

  @Override
  public Gps getGpsInfo(String road) {

    NaverGeoResponse gpsInfo = naverFeignClient.getGpsInfo(road, clientId, clientSecret, "application/json");
    return Gps.createGps(gpsInfo.addresses().get(0));
  }

  @Override
  public Direction getDistance(GpsDistanceCommand command) {
    NaverDirectionResponse distanceInfo = naverFeignClient.getDistanceInfo(command.depart(), command.arrival(),
        clientId, clientSecret, "application/json");

    return Direction.createDirection(distanceInfo);
  }
}
