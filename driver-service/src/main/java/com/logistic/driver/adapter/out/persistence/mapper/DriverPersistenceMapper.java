package com.logistic.driver.adapter.out.persistence.mapper;

import com.logistic.driver.adapter.out.persistence.model.entity.DriverEntity;
import com.logistic.driver.adapter.out.persistence.model.entity.DriverViewEntity;
import com.logistic.driver.domain.model.Driver;
import com.logistic.driver.domain.model.DriverView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverPersistenceMapper {
  Driver toDomain(DriverEntity driverEntity);

  DriverEntity toEntity(Driver driver);

  DriverView toView(DriverViewEntity driverViewEntity);

  DriverViewEntity toEntity(DriverView driverView);
}
