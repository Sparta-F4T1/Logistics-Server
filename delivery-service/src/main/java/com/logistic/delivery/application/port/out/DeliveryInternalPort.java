package com.logistic.delivery.application.port.out;

import com.logistic.delivery.domain.vo.dto.HubDriverInfo;
import com.logistic.delivery.domain.vo.dto.HubRouteInfo;
import java.util.List;

public interface DeliveryInternalPort {
  List<HubRouteInfo> getHubRoutes(Long departHubId, Long arrivalHubId);
  List<HubDriverInfo> getHubDrivers(List<HubRouteInfo> hubRoutes);

}
