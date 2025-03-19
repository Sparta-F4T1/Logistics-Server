package com.logistic.hub.adapter.out.persistence.mapper;

import com.logistic.hub.adapter.out.persistence.entity.RouteEntity;
import com.logistic.hub.domain.Route;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoutePersistenceMapper {
  Route toDomain(RouteEntity routeEntity);

  RouteEntity toEntity(Route hub);
}

