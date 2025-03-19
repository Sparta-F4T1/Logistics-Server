package com.logistic.hub.application.port.out.persistence;

import com.logistic.hub.adaptor.in.web.response.RouteHistoryResponse;
import com.logistic.hub.domain.Route;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoutePersistencePort {
  Optional<Route> save(Route route);

  Optional<Route> findById(Long hubRouteId);

  void delete(Route route);

  Page<RouteHistoryResponse> findAllBySearch(String search, Pageable pageable);

  Optional<Route> findByDepartAndArrival(Long departHubId, Long arrivalHubId);

  List<Route> findAll();
}
