package com.logistic.hub.adapter.out.persistence;

import com.logistic.common.annotation.Adapter;
import com.logistic.hub.adapter.in.web.response.HubHistoryResponse;
import com.logistic.hub.adapter.out.persistence.entity.HubEntity;
import com.logistic.hub.adapter.out.persistence.mapper.HubPersistenceMapper;
import com.logistic.hub.adapter.out.persistence.repository.HubJpaRepository;
import com.logistic.hub.adapter.out.persistence.repository.HubQueryDslRepository;
import com.logistic.hub.application.port.out.persistence.HubPersistencePort;
import com.logistic.hub.domain.Hub;
import com.logistic.hub.domain.exception.HubNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Adapter
@RequiredArgsConstructor
public class HubPersistenceAdaptor implements HubPersistencePort {
  private final HubJpaRepository hubJpaRepository;
  private final HubPersistenceMapper hubPersistenceMapper;
  private final HubQueryDslRepository hubQueryDslRepository;

  @Override
  public Hub save(Hub hub) {
    HubEntity hubEntity = hubJpaRepository.save(hubPersistenceMapper.toEntity(hub));
    return hubPersistenceMapper.toDomain(hubEntity);
  }

  @Override
  public Page<HubHistoryResponse> findAllBySearch(String search, Pageable pageable) {

    return hubQueryDslRepository.findAllBySearch(search, pageable);
  }

  @Override
  public Hub findById(Long hubId) {
    HubEntity hubEntity = hubJpaRepository.findById(hubId).orElseThrow(() -> new HubNotFoundException("존재하지 않는 허브입니다"));

    return hubPersistenceMapper.toDomain(hubEntity);
  }

  @Override
  public void delete(Hub hub) {
    HubEntity hubEntity = hubJpaRepository.findById(hub.getId())
        .orElseThrow(() -> new HubNotFoundException("존재하지 않는 허브입니다"));

    hubEntity.delete(true, "test");
  }

  @Override
  public boolean existsHub(Long hubId) {
    return hubJpaRepository.existsById(hubId);
  }

}
