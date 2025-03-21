package com.logistic.hub.adapter.in.internal.mapper;

import com.logistic.common.internal.response.RouteClientResponse;
import com.logistic.hub.domain.Route;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RouteInternalMapper {

  RouteClientResponse toRouteClientResponse(Route route);
}
