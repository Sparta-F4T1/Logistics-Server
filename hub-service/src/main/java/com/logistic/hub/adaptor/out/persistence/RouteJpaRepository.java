package com.logistic.hub.adaptor.out.persistence;

import com.logistic.hub.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteJpaRepository extends JpaRepository<Route, Long> {
}
