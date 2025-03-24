package com.logistic.hub.adapter.in.web.mapper;

import com.logistic.common.passport.model.Passport;
import com.logistic.hub.adapter.in.web.request.RouteCreateRequest;
import com.logistic.hub.adapter.in.web.response.RouteCreateResponse;
import com.logistic.hub.adapter.in.web.response.RouteDetailsResponse;
import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.application.port.in.command.RouteDeleteByHubIdCommand;
import com.logistic.hub.application.port.in.command.RouteDeleteCommand;
import com.logistic.hub.application.port.in.query.RouteFindQuery;
import com.logistic.hub.application.port.in.query.RouteSearchQuery;
import com.logistic.hub.application.service.dto.RouteDetailsDto;
import com.logistic.hub.domain.Route;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RouteWebMapper {
  RouteCreateCommand toRouteCreateCommand(RouteCreateRequest request, Passport passport);

  @Mapping(source = "id", target = "routeId")
  RouteCreateResponse toRouteCreateResponse(Route route);

  RouteDetailsResponse toRouteDetailsResponse(RouteDetailsDto routeDetailsDto);

  RouteFindQuery toFindQuery(Long routeId);

  RouteSearchQuery toSearchQuery(int page, int size, String searchType, String search);

  RouteDeleteByHubIdCommand toDeleteByHubIdCommand(Long hubId, Passport passport);

  RouteDeleteCommand toRouteDeleteCommand(Long routeId, Passport passport);
}
