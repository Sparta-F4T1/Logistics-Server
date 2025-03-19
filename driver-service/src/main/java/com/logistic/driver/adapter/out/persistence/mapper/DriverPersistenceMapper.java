package com.logistic.driver.adapter.out.persistence.mapper;

import com.logistic.driver.adapter.out.persistence.model.entity.DriverEntity;
import com.logistic.driver.domain.Driver;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverPersistenceMapper {
  Driver toDomain(DriverEntity driverEntity);

  DriverEntity toEntity(Driver driver);
}
