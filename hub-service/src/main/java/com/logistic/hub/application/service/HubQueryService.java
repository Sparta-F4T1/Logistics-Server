package com.logistic.hub.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.hub.application.port.in.HubQueryUseCase;
import com.logistic.hub.application.port.in.query.HubFindQuery;
import com.logistic.hub.application.port.in.query.HubSearchQuery;
import com.logistic.hub.application.port.out.persistence.HubPersistencePort;
import com.logistic.hub.application.service.dto.HubHistoryDto;
import com.logistic.hub.config.RestPage;
import com.logistic.hub.domain.Hub;
import com.logistic.hub.domain.exception.HubAlreadyDeletedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@UseCase
@Transactional
@RequiredArgsConstructor
public class HubQueryService implements HubQueryUseCase {
  private final HubPersistencePort hubPersistencePort;

  @Override
  @Cacheable(cacheNames = "hubList", key = "{ #hubSearchQuery.page(),#hubSearchQuery.size(),#hubSearchQuery.search()}")
  public RestPage<HubHistoryDto> getHubList(HubSearchQuery hubSearchQuery) {
    Sort.Direction direction = Direction.ASC;  //'가'부터 허브명으로 정렬되도록 조회
    Sort sort1 = Sort.by(direction, "hubName");
    Pageable pageable = PageRequest.of(hubSearchQuery.page(), hubSearchQuery.size(), sort1);

    Page<HubHistoryDto> list = hubPersistencePort.findAllBySearch(hubSearchQuery.search(), pageable);
    return new RestPage<>(list);
  }

  @Override
  public Hub getHubDetails(HubFindQuery hubFindQuery) {
    Hub hub = hubPersistencePort.findById(hubFindQuery.hubId());
    isDeleted(hub);
    return hub;
  }

  private void isDeleted(Hub hub) {
    if (hub.getIsDeleted()) {
      throw new HubAlreadyDeletedException("이미 삭제된 허브입니다.");
    }
  }
}
