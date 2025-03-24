package com.logistic.hub.adapter.in.internal.mapper;

import com.logistic.common.internal.response.RouteClientResponse;
import com.logistic.hub.domain.Route;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RouteInternalMapper {

  @Mapping(source = "id", target = "routeId")
  RouteClientResponse toRouteClientResponse(Route route);
}
