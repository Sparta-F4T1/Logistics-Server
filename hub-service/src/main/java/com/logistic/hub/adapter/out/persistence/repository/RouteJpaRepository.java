package com.logistic.hub.adapter.out.persistence.repository;

import com.logistic.hub.adapter.out.persistence.entity.RouteEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteJpaRepository extends JpaRepository<RouteEntity, Long> {

  List<RouteEntity> findAllByIsDeletedFalse();

  Optional<RouteEntity> findBydepartHubIdAndArrivalHubIdAndIsDeletedFalse(Long departHubId, Long arrivalHubId);
}
