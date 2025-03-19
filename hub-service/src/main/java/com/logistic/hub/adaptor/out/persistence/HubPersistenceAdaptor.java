package com.logistic.hub.adaptor.out.persistence;

import com.logistic.common.annotation.Adapter;
import com.logistic.hub.adaptor.in.web.response.HubHistoryResponse;
import com.logistic.hub.adaptor.out.persistence.entity.HubEntity;
import com.logistic.hub.adaptor.out.persistence.mapper.HubPersistenceMapper;
import com.logistic.hub.adaptor.out.persistence.repository.HubJpaRepository;
import com.logistic.hub.adaptor.out.persistence.repository.HubQueryDslRepository;
import com.logistic.hub.application.port.out.persistence.HubPersistencePort;
import com.logistic.hub.domain.Hub;
import java.util.Optional;
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
  public Optional<Hub> save(Hub hub) {
    HubEntity hubEntity = hubJpaRepository.save(hubPersistenceMapper.toEntity(hub));
    return Optional.of(hubPersistenceMapper.toDomain(hubEntity));
  }

  @Override
  public Page<HubHistoryResponse> findAllBySearch(String search, Pageable pageable) {

    return hubQueryDslRepository.findAllBySearch(search, pageable);
  }

  @Override
  public Optional<Hub> findById(Long hubId) {
    Optional<HubEntity> hubEntity = hubJpaRepository.findById(hubId);

    return hubEntity.map(hubPersistenceMapper::toDomain);
  }

  @Override
  public void delete(Hub hub) {
    Optional<HubEntity> hubEntity = hubJpaRepository.findById(hub.getId());

    hubEntity.ifPresent(entity -> entity.delete(true, "test")); //임시
  }

}
