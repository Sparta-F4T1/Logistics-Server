package com.logistic.driver.adapter.out.persistence;

import com.logistic.common.annotation.Adapter;
import com.logistic.driver.adapter.out.persistence.mapper.DriverPersistenceMapper;
import com.logistic.driver.adapter.out.persistence.model.entity.DriverEntity;
import com.logistic.driver.adapter.out.persistence.repository.DriverJpaRepository;
import com.logistic.driver.adapter.out.persistence.repository.DriverQueryDslRepository;
import com.logistic.driver.application.port.in.query.SearchDriverQuery;
import com.logistic.driver.application.port.out.DriverCommandPersistencePort;
import com.logistic.driver.domain.exception.CustomNotFoundException.DriverNotFoundException;
import com.logistic.driver.domain.model.Driver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@Adapter
@RequiredArgsConstructor
public class DriverCommandPersistenceAdapter implements DriverCommandPersistencePort {
  private final DriverJpaRepository jpaRepository;
  private final DriverQueryDslRepository queryDslRepository;
  private final DriverPersistenceMapper mapper;

  @Override
  public Driver save(final Driver driver) {
    final DriverEntity driverEntity = jpaRepository.save(mapper.toEntity(driver));
    return mapper.toDomain(driverEntity);
  }

  @Override
  public Driver findById(final String driverId) {
    final DriverEntity driverEntity = jpaRepository.findById(driverId).orElseThrow(DriverNotFoundException::new);
    return mapper.toDomain(driverEntity);
  }

  @Override
  public List<Driver> findAll(List<String> driverIds) {
    final List<DriverEntity> driverEntityList = jpaRepository.findAllById(driverIds);
    return driverEntityList.stream().map(mapper::toDomain).toList();
  }

  @Override
  public Page<Driver> search(final SearchDriverQuery query) {
    return queryDslRepository.search(query).map(mapper::toDomain);
  }
}
