package com.logistic.hub.application.port.in;

import com.logistic.hub.adaptor.in.web.response.HubHistoryListResponse;
import com.logistic.hub.application.port.in.command.DepartArrivalCommand;
import com.logistic.hub.application.port.in.command.HubCreateCommand;
import com.logistic.hub.application.port.in.command.HubUpdateCommand;
import com.logistic.hub.domain.Hub;

public interface HubUseCase {

  Hub createHub(HubCreateCommand command);

  HubHistoryListResponse getHubList(int page, int size, String searchType,
                                    String search);

  void updateHub(Long hubId, HubUpdateCommand command);

  void deleteHub(Long hubId);

  Hub getHubDetails(Long hubId);

  boolean existsHub(Long hubId);

  DepartArrivalCommand getHubNameInfo(Long departHubId, Long arrivalHubId);
}
