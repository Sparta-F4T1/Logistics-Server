package com.logistic.hub.application.port.in;

import com.logistic.hub.application.port.in.query.HubFindQuery;
import com.logistic.hub.application.port.in.query.HubSearchQuery;
import com.logistic.hub.application.service.dto.HubHistoryDto;
import com.logistic.hub.domain.Hub;
import org.springframework.data.domain.Page;

public interface HubQueryUseCase {

  Page<HubHistoryDto> getHubList(HubSearchQuery hubSearchQuery);

  Hub getHubDetails(HubFindQuery hubFindQuery);
}
