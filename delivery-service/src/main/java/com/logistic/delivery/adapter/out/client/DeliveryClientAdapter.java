package com.logistic.delivery.adapter.out.client;


import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.request.DriverClientRequest;
import com.logistic.common.internal.request.DriverClientRequest.hubRoute;
import com.logistic.delivery.adapter.out.client.mapper.DeliveryClientMapper;
import com.logistic.delivery.application.port.out.DeliveryInternalPort;
import com.logistic.delivery.domain.vo.dto.HubDriverInfo;
import com.logistic.delivery.domain.vo.dto.HubRouteInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class DeliveryClientAdapter implements DeliveryInternalPort {
  private final DeliveryClientMapper mapper;
  private final DriverFeignClient driverFeignClient;
  private final RouteFeignClient routeFeignClient;

  public List<HubRouteInfo> getHubRoutes(Long departHubId, Long arrivalHubId) {
    return routeFeignClient.shortestPath(departHubId, arrivalHubId)
        .stream()
        .map(mapper::toHubRoute)
        .toList();
  }

  public List<HubDriverInfo> getHubDrivers(List<HubRouteInfo> hubRoutes) {
    return driverFeignClient.getHubDriver(createDriverClientRequest(hubRoutes))
        .stream()
        .map(mapper::toHubDriver)
        .toList();
  }

  private DriverClientRequest createDriverClientRequest(List<HubRouteInfo> hubRoutes){
    List<hubRoute> hubRouteList = hubRoutes.stream()
        .map(route -> new hubRoute(route.departHubId(), route.arrivalHubId()))
        .toList();
    return new DriverClientRequest(hubRouteList);
  }

}
