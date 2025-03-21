package com.logistic.hub.application.port.in;

import com.logistic.hub.application.port.in.command.HubCreateCommand;
import com.logistic.hub.application.port.in.command.HubUpdateCommand;
import com.logistic.hub.application.service.dto.DepartArrivalDto;
import com.logistic.hub.domain.Hub;

public interface HubUseCase {

  Hub createHub(HubCreateCommand command);

  void updateHub(Long hubId, HubUpdateCommand command);

  void deleteHub(Long hubId);

  boolean existsHub(Long hubId);

  DepartArrivalDto getHubNameInfo(Long departHubId, Long arrivalHubId);
}
