package com.logistic.driver.adapter.out.persistence;

import com.logistic.common.annotation.Adapter;
import com.logistic.driver.adapter.out.persistence.mapper.DriverPersistenceMapper;
import com.logistic.driver.adapter.out.persistence.model.entity.DriverViewEntity;
import com.logistic.driver.adapter.out.persistence.repository.DriverViewJpaRepository;
import com.logistic.driver.adapter.out.persistence.repository.DriverViewQueryDslRepository;
import com.logistic.driver.application.port.in.query.SearchDriverQuery;
import com.logistic.driver.application.port.out.DriverQueryPersistencePort;
import com.logistic.driver.domain.exception.CustomNotFoundException.DriverNotFoundException;
import com.logistic.driver.domain.model.DriverView;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@Adapter
@RequiredArgsConstructor
public class DriverQueryPersistenceAdapter implements DriverQueryPersistencePort {
  private final DriverPersistenceMapper mapper;
  private final DriverViewJpaRepository jpaRepository;
  private final DriverViewQueryDslRepository queryDslRepository;

  @Override
  public DriverView save(final DriverView driverView) {
    final DriverViewEntity driverviewEntity = jpaRepository.save(mapper.toEntity(driverView));
    return mapper.toView(driverviewEntity);
  }

  @Override
  public DriverView findById(final String driverId) {
    final DriverViewEntity driverEntity = jpaRepository.findById(driverId).orElseThrow(DriverNotFoundException::new);
    return mapper.toView(driverEntity);
  }

  @Override
  public List<DriverView> findAll(List<String> driverIds) {
    final List<DriverViewEntity> driverEntityList = jpaRepository.findAllById(driverIds);
    return driverEntityList.stream().map(mapper::toView).toList();
  }

  @Override
  public Page<DriverView> search(final SearchDriverQuery query) {
    return queryDslRepository.search(query).map(mapper::toView);
  }
}
