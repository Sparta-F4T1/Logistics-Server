package com.logistic.hub.adaptor.in.web.mapper;

import com.logistic.hub.adaptor.in.web.request.HubCreateRequest;
import com.logistic.hub.adaptor.in.web.request.HubUpdateRequest;
import com.logistic.hub.adaptor.in.web.response.HubCreateResponse;
import com.logistic.hub.adaptor.in.web.response.HubDetailsResponse;
import com.logistic.hub.application.port.in.command.HubCreateCommand;
import com.logistic.hub.application.port.in.command.HubUpdateCommand;
import com.logistic.hub.domain.Hub;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HubWebMapper {

  HubCreateCommand toHubCreateCommand(HubCreateRequest hubCreateRequest);

  HubUpdateCommand toHubUpdateCommand(HubUpdateRequest hubUpdateRequest);

  @Mapping(source = "id", target = "hubId")
  HubCreateResponse toHubCreateResponse(Hub hub);

  @Mapping(source = "id", target = "hubId")
  HubDetailsResponse toHubDetailsResponse(Hub hub);


}
