package com.logistic.hub.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.hub.application.port.in.HubUseCase;
import com.logistic.hub.application.port.in.command.HubCreateCommand;
import com.logistic.hub.application.port.in.command.HubUpdateCommand;
import com.logistic.hub.application.port.out.client.GpsInternalPort;
import com.logistic.hub.application.port.out.persistence.HubPersistencePort;
import com.logistic.hub.application.service.dto.DepartArrivalDto;
import com.logistic.hub.domain.Hub;
import com.logistic.hub.domain.command.AddressCommand;
import com.logistic.hub.domain.exception.HubAlreadyDeletedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;

@UseCase
@Transactional
@RequiredArgsConstructor
public class HubService implements HubUseCase {
  private final HubPersistencePort hubPersistencePort;
  private final GpsInternalPort gpsInternalPort;

  @Override
  @CacheEvict(cacheNames = "hubList", allEntries = true)
  public Hub createHub(HubCreateCommand hubCommand) {
    AddressCommand addressCommand = gpsInternalPort.getAddressCommand(hubCommand.roadAddress(),
        hubCommand.jibunAddress()); //임시 0, 37로 고정)
    Hub hub = Hub.createHub(hubCommand, addressCommand);

    return hubPersistencePort.save(hub);
  }


  @Override
  @CacheEvict(cacheNames = "hubList", allEntries = true)
  public void updateHub(Long hubId, HubUpdateCommand command) {
    Hub hub = getOrElseThrow(hubId);
    isDeleted(hub);
    AddressCommand addressCommand = gpsInternalPort.getAddressCommand(command.roadAddress(),
        command.jibunAddress()); //임시 (300, 37로 고정)
    hub.update(command, addressCommand);

    hubPersistencePort.save(hub);
  }

  @Override
  @CacheEvict(cacheNames = "hubList", allEntries = true)
  public void deleteHub(Long hubId) {
    Hub hub = getOrElseThrow(hubId);
    isDeleted(hub);
    hubPersistencePort.delete(hub);
  }

  private Hub getOrElseThrow(Long hubId) {
    return hubPersistencePort.findById(hubId);
  }

  @Override
  public boolean existsHub(Long hubId) {
    return hubPersistencePort.existsHub(hubId);
  }

  @Override
  public DepartArrivalDto getHubNameInfo(Long departHubId, Long arrivalHubId) {
    Hub departHub = getOrElseThrow(departHubId);
    Hub arrivalHub = getOrElseThrow(arrivalHubId);

    return new DepartArrivalDto(departHub.getHubName(), arrivalHub.getHubName());
  }

  private void isDeleted(Hub hub) {
    if (hub.getIsDeleted()) {
      throw new HubAlreadyDeletedException("이미 삭제된 허브입니다.");
    }
  }
}

