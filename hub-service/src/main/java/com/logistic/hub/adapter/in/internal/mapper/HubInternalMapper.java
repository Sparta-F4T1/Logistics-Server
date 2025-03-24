package com.logistic.hub.adapter.in.internal.mapper;

import com.logistic.common.internal.response.HubClientResponse;
import com.logistic.hub.domain.Hub;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface HubInternalMapper {

  @Mapping(source = "id", target = "hubId")
  HubClientResponse toHubClientResponse(Hub hub);
}
