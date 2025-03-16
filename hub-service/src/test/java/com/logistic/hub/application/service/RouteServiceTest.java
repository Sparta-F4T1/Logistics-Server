package com.logistic.hub.application.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import com.logistic.hub.application.port.in.command.HubCreateCommand;
import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.application.port.out.HubPersistencePort;
import com.logistic.hub.domain.Hub;
import com.logistic.hub.domain.Route;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class RouteServiceTest {

  @Autowired
  private RouteService routeService;
  @MockitoBean
  private HubPersistencePort hubPersistencePort;

  @Test
  @DisplayName("허브경로 생성")
  void createHubRoute() {

    HubCreateCommand hubCreateCommand1 = new HubCreateCommand("CENTRAL", "경기남부", "도로명주소", "지번주소");
    HubCreateCommand hubCreateCommand2 = new HubCreateCommand("REGIONAL", "경기북부", "도로명주소", "지번주소");

    RouteCreateCommand command = new RouteCreateCommand(1L, 2L);
    Hub mockHub = Hub.builder().build();
    Mockito.when(hubPersistencePort.findById(any())).thenReturn(Optional.of(mockHub));
    Route route = routeService.createHubRoute(command);

    assertEquals(1L, route.getDepartHubId());
    assertEquals(2L, route.getArrivalHubId());

  }
}
