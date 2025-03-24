package com.logistic.hub.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.common.passport.model.Passport;
import com.logistic.common.passport.model.RoleType;
import com.logistic.hub.application.port.in.HubUseCase;
import com.logistic.hub.application.port.in.RouteUseCase;
import com.logistic.hub.application.port.in.command.HubCreateCommand;
import com.logistic.hub.application.port.in.command.HubDeleteCommand;
import com.logistic.hub.application.port.in.command.HubUpdateCommand;
import com.logistic.hub.application.port.in.command.RouteDeleteByHubIdCommand;
import com.logistic.hub.application.port.out.client.GpsInternalPort;
import com.logistic.hub.application.port.out.persistence.HubPersistencePort;
import com.logistic.hub.domain.Hub;
import com.logistic.hub.domain.command.AddressCommand;
import com.logistic.hub.domain.exception.HubAlreadyDeletedException;
import com.logistic.hub.domain.exception.HubPermissionDeniedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

@UseCase
@Transactional
@RequiredArgsConstructor
public class HubService implements HubUseCase {
  private final HubPersistencePort hubPersistencePort;
  private final GpsInternalPort gpsInternalPort;
  private final RouteUseCase routeUseCase;

  @Override
  @CacheEvict(cacheNames = "hubList", allEntries = true)
  public Hub createHub(HubCreateCommand hubCommand) {
    checkAuthority(hubCommand.passport());
    AddressCommand addressCommand = gpsInternalPort.getAddressCommand(hubCommand.roadAddress(),
        hubCommand.jibunAddress());
    Hub hub = Hub.createHub(hubCommand, addressCommand);

    return hubPersistencePort.save(hub);
  }

  @Override
  @Caching(evict = {
      @CacheEvict(cacheNames = "hubList", allEntries = true),
      @CacheEvict(cacheNames = "routeList", allEntries = true),
      @CacheEvict(cacheNames = "hub", key = "#hubCommand.hubId()")
  })
  public void updateHub(HubUpdateCommand hubCommand, RouteDeleteByHubIdCommand routeCommand) {
    checkAuthority(hubCommand.passport());
    Hub hub = getOrElseThrow(hubCommand.hubId());
    isDeleted(hub);
    AddressCommand addressCommand = gpsInternalPort.getAddressCommand(hubCommand.roadAddress(),
        hubCommand.jibunAddress());
    hub.update(hubCommand, addressCommand);

    hubPersistencePort.save(hub);

    deleteRelatedRoutes(routeCommand);
  }


  @Override
  @Caching(evict = {
      @CacheEvict(cacheNames = "hubList", allEntries = true),
      @CacheEvict(cacheNames = "routeList", allEntries = true),
      @CacheEvict(cacheNames = "hub", key = "#command.hubId()")
  })
  public void deleteHub(HubDeleteCommand command, RouteDeleteByHubIdCommand routeCommand) {
    checkAuthority(command.passport());
    Hub hub = getOrElseThrow(command.hubId());
    isDeleted(hub);
    hubPersistencePort.delete(hub, command.passport().getUserInfo().getUserId());

    deleteRelatedRoutes(routeCommand);
  }


  private Hub getOrElseThrow(Long hubId) {
    return hubPersistencePort.findById(hubId);
  }

  private void isDeleted(Hub hub) {
    if (hub.getIsDeleted()) {
      throw new HubAlreadyDeletedException("이미 삭제된 허브입니다.");
    }
  }

  private void deleteRelatedRoutes(RouteDeleteByHubIdCommand routeCommand) {
    routeUseCase.deleteHubRouteByHubId(routeCommand);
  }

  private void checkAuthority(Passport passport) {
    System.out.println(passport.getUserInfo().getRole() + " " + passport.getUserInfo().getUserId());
    RoleType roleType = RoleType.valueOf(passport.getUserInfo().getRole());

    if (roleType != RoleType.MASTER_ADMIN) {
      throw new HubPermissionDeniedException("권한이 없습니다");
    }
  }

}

