package com.logistic.gps.application.port.in;

import com.logistic.gps.application.port.in.command.GpsDistanceCommand;
import com.logistic.gps.application.port.in.command.GpsInfoCommand;
import com.logistic.gps.domain.Direction;
import com.logistic.gps.domain.Gps;

public interface GpsUseCase {

  Gps findGps(GpsInfoCommand command);

  Direction findDistance(GpsDistanceCommand command);
}
