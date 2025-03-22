package com.logistic.gps.application.port.out;

import com.logistic.gps.application.port.in.command.GpsDistanceCommand;
import com.logistic.gps.domain.Direction;
import com.logistic.gps.domain.Gps;

public interface GpsExternalPort {

  Gps getGpsInfo(String road);

  Direction getDistance(GpsDistanceCommand command);
}
