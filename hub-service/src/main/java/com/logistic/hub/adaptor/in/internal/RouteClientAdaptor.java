package com.logistic.hub.adaptor.in.internal;

import com.logistic.common.annotation.Adapter;
import com.logistic.hub.adaptor.in.internal.response.HubClientShortestPathResponse;
import com.logistic.hub.application.port.in.RouteUseCase;
import com.logistic.hub.application.port.in.command.DepartArrivalIdCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/hubRoutes")
public class RouteClientAdaptor {
  private final RouteUseCase routeUseCase;

  @GetMapping("/route")
  public HubClientShortestPathResponse//공통모듈 추가 시 HubClientResponse로 변경 예정
  getRoute(@RequestParam Long departHubId, @RequestParam Long arrivalHubId) {
    DepartArrivalIdCommand command = new DepartArrivalIdCommand(departHubId, arrivalHubId);

    HubClientShortestPathResponse response = routeUseCase.getShortestPath(command);

    return response;
  }
}
