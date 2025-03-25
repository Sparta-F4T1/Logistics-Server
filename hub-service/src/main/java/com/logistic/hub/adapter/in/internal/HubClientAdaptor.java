package com.logistic.hub.adapter.in.internal;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.response.HubClientResponse;
import com.logistic.hub.adapter.in.internal.mapper.HubInternalMapper;
import com.logistic.hub.application.port.in.HubQueryUseCase;
import com.logistic.hub.application.port.in.query.HubFindQuery;
import com.logistic.hub.application.port.in.query.HubListQuery;
import com.logistic.hub.domain.Hub;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/hubs")
public class HubClientAdaptor {
  private final HubQueryUseCase hubQueryUseCase;
  private final HubInternalMapper hubInternalMapper;

  @GetMapping
  List<HubClientResponse> findHubList(@RequestParam List<Long> hubId) {
    List<Hub> hubList = hubQueryUseCase.findHubList(new HubListQuery(hubId));

    return hubList.stream().map(hubInternalMapper::toHubClientResponse).collect(Collectors.toList());
  }

  @GetMapping("/{hubId}")
  HubClientResponse findHub(@PathVariable("hubId") Long hubId) {
    Hub hub = hubQueryUseCase.getHubDetails(new HubFindQuery(hubId));
    return hubInternalMapper.toHubClientResponse(hub);
  }


}
