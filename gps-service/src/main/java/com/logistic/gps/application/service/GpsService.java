package com.logistic.gps.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.gps.application.port.in.GpsUseCase;
import com.logistic.gps.application.port.in.command.GpsDistanceCommand;
import com.logistic.gps.application.port.in.command.GpsInfoCommand;
import com.logistic.gps.application.port.out.GpsExternalPort;
import com.logistic.gps.domain.Direction;
import com.logistic.gps.domain.Gps;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GpsService implements GpsUseCase {
  private final GpsExternalPort gpsExternalPort;


  @Override
  public Gps findGps(GpsInfoCommand command) {
    Gps gpsInfo = gpsExternalPort.getGpsInfo(command.road());

    return gpsInfo;
  }

  @Override
  public Direction findDistance(GpsDistanceCommand command) {
    Direction direction = gpsExternalPort.getDistance(command);
    return direction;
  }
}
