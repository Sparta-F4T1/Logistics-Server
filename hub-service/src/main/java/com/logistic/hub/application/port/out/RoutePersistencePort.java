package com.logistic.hub.application.port.out;

import com.logistic.hub.domain.Route;
import java.util.Optional;

public interface RoutePersistencePort {
  Optional<Route> save(Route route);

  Optional<Route> findById(Long hubRouteId);

  void delete(Route route);
}
