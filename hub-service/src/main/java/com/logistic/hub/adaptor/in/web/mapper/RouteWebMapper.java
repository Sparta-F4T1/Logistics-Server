package com.logistic.hub.adaptor.in.web.mapper;

import com.logistic.hub.adaptor.in.web.request.RouteCreateRequest;
import com.logistic.hub.adaptor.in.web.response.RouteCreateResponse;
import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.domain.Route;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RouteWebMapper {
  RouteCreateCommand toRouteCreateCommand(RouteCreateRequest request);

  RouteCreateResponse toRouteCreateResponse(Route route);
}
