package com.logistic.hub.adapter.out.persistence.mapper;

import com.logistic.hub.adapter.out.persistence.entity.HubEntity;
import com.logistic.hub.domain.Hub;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HubPersistenceMapper {
  Hub toDomain(HubEntity hubEntity);

  HubEntity toEntity(Hub hub);
}
