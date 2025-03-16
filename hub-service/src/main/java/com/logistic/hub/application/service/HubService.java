package com.logistic.hub.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.hub.adaptor.in.web.response.HubHistoryListResponse;
import com.logistic.hub.adaptor.in.web.response.HubHistoryResponse;
import com.logistic.hub.application.port.in.HubUseCase;
import com.logistic.hub.application.port.in.command.HubCreateCommand;
import com.logistic.hub.application.port.in.command.HubUpdateCommand;
import com.logistic.hub.application.port.out.HubPersistencePort;
import com.logistic.hub.application.port.out.NaverClientPort;
import com.logistic.hub.domain.Hub;
import com.logistic.hub.domain.command.AddressCommand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@UseCase
@Transactional
@RequiredArgsConstructor
public class HubService implements HubUseCase {
  private final HubPersistencePort hubPersistencePort;
  private final NaverClientPort naverClientPort;

  @Override
  public Hub createHub(HubCreateCommand hubCommand) {
    AddressCommand addressCommand = naverClientPort.getAddressCommand(hubCommand.roadAddress(),
        hubCommand.jibunAddress()); //임시 (300, 37로 고정)
    Hub hub = Hub.createHub(hubCommand, addressCommand);

    return hubPersistencePort.save(hub).orElseThrow(() -> new IllegalArgumentException("허브 생성 실패"));
  }

  @Override
  public HubHistoryListResponse getHubList(int page, int size, String searchType,
                                           String search) {
    Sort.Direction direction = Direction.ASC;  //'가'부터 허브명으로 정렬되도록 조회
    Sort sort1 = Sort.by(direction, "hubName");
    Pageable pageable = PageRequest.of(page, size, sort1);

    Page<HubHistoryResponse> list = hubPersistencePort.findAllBySearch(search, pageable);
    HubHistoryListResponse hubList = HubHistoryListResponse.from(list);
    return hubList;
  }

  @Override
  public Hub getHubDetails(Long hubId) {
    Hub hub = hubPersistencePort.findById(hubId).orElseThrow(() -> new IllegalArgumentException("허브가 존재하지 않습니다"));

    isDeleted(hub);

    return hub;
  }

  @Override
  public void updateHub(Long hubId, HubUpdateCommand command) {
    Hub hub = hubPersistencePort.findById(hubId).orElseThrow(() -> new IllegalArgumentException("허브가 존재하지 않습니다"));
    isDeleted(hub);
    AddressCommand addressCommand = naverClientPort.getAddressCommand(command.roadAddress(),
        command.jibunAddress()); //임시 (300, 37로 고정)
    hub.update(command, addressCommand);

    hubPersistencePort.save(hub);
  }

  @Override
  public void deleteHub(Long hubId) {
    Hub hub = hubPersistencePort.findById(hubId).orElseThrow(() -> new IllegalArgumentException("허브가 존재하지 않습니다"));
    isDeleted(hub);
    hubPersistencePort.delete(hub);
  }

  private static void isDeleted(Hub hub) {
    if (hub.getIsDeleted()) {
      throw new IllegalArgumentException("이미 삭제된 허브입니다.");
    }
  }
}
