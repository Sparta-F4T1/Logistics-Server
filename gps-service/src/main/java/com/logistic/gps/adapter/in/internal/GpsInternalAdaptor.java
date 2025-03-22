package com.logistic.gps.adapter.in.internal;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.request.GpsClientRequest;
import com.logistic.common.internal.response.GpsClientResponse;
import com.logistic.gps.application.port.in.GpsUseCase;
import com.logistic.gps.application.port.in.command.GpsDistanceCommand;
import com.logistic.gps.application.port.in.command.GpsInfoCommand;
import com.logistic.gps.domain.Direction;
import com.logistic.gps.domain.Gps;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/gps")
public class GpsInternalAdaptor {
  private final GpsUseCase gpsUseCase;


  @GetMapping("/{gpsId}")
    //위도, 경도 반환
  GpsClientResponse findGps(@PathVariable("gpsId") Long gpsId,
                            @ModelAttribute GpsClientRequest request
  ) {

    GpsInfoCommand command = new GpsInfoCommand(request.road());
    Gps gps = gpsUseCase.findGps(command);

    return new GpsClientResponse(gpsId, gps.getRoad(), gps.getJibun(), gps.getLatitude(), gps.getLongitude(), null,
        null);
  }

  @GetMapping
    // 허브 간 거리, 시간 계산
  GpsClientResponse findDistance(@RequestParam String depart, @RequestParam String arrival) {

    GpsDistanceCommand command = new GpsDistanceCommand(depart, arrival);

    Direction direction = gpsUseCase.findDistance(command);

    return new GpsClientResponse(null, null, null, null, null, direction.getDistance(), direction.getDuration());
  }
}
