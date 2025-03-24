package com.logistic.hub.application.port.in;

import com.logistic.hub.application.port.in.query.HubFindQuery;
import com.logistic.hub.application.port.in.query.HubListQuery;
import com.logistic.hub.application.port.in.query.HubSearchQuery;
import com.logistic.hub.application.service.dto.DepartArrivalDto;
import com.logistic.hub.application.service.dto.HubHistoryDto;
import com.logistic.hub.domain.Hub;
import java.util.List;
import org.springframework.data.domain.Page;

public interface HubQueryUseCase {

  Page<HubHistoryDto> search(HubSearchQuery hubSearchQuery);

  List<Hub> findHubList(HubListQuery query);

  Hub getHubDetails(HubFindQuery hubFindQuery);

  DepartArrivalDto getHubNameInfo(Long departHubId, Long arrivalHubId);

  boolean existsHub(Long hubId);
}
