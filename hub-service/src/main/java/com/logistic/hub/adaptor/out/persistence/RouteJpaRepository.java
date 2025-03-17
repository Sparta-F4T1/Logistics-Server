package com.logistic.hub.adaptor.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteJpaRepository extends JpaRepository<RouteEntity, Long> {
  Optional<RouteEntity> findBydepartHubIdAndArrivalHubId(Long departHubId, Long arrivalHubId);
}
