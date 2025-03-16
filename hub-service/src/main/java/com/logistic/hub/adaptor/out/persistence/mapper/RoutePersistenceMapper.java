package com.logistic.hub.adaptor.out.persistence.mapper;

import com.logistic.hub.adaptor.out.persistence.RouteEntity;
import com.logistic.hub.domain.Route;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoutePersistenceMapper {
  Route toDomain(RouteEntity routeEntity);

  RouteEntity toEntity(Route hub);
}

