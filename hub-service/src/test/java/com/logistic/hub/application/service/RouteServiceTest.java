package com.logistic.hub.application.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import com.logistic.hub.adaptor.in.web.response.RouteDetailsResponse;
import com.logistic.hub.adaptor.in.web.response.RouteHistoryListResponse;
import com.logistic.hub.application.port.in.HubUseCase;
import com.logistic.hub.application.port.in.command.DepartArrivalCommand;
import com.logistic.hub.application.port.in.command.HubCreateCommand;
import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.application.port.out.RoutePersistencePort;
import com.logistic.hub.domain.Hub;
import com.logistic.hub.domain.Route;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class RouteServiceTest {

  @Autowired
  private RouteService routeService;
  @Autowired
  private HubUseCase hubUseCase;
  @Autowired
  private RoutePersistencePort routePersistencePort;

  @Test
  @DisplayName("허브경로 생성")
  void createHubRoute() {
    //given
    HubCreateCommand hubCreateCommand1 = new HubCreateCommand("CENTRAL", "경기남부", "도로명주소", "지번주소");
    HubCreateCommand hubCreateCommand2 = new HubCreateCommand("REGIONAL", "경기북부", "도로명주소", "지번주소");

    hubUseCase.createHub(hubCreateCommand1);
    hubUseCase.createHub(hubCreateCommand2);

    RouteCreateCommand command = new RouteCreateCommand(1L, 2L);
    Hub mockHub = Hub.builder().build();
    Mockito.when(hubUseCase.existsHub(any())).thenReturn(true);

    //when
    Route route = routeService.createHubRoute(command);

    //then
    assertEquals(1L, route.getDepartHubId());
    assertEquals(2L, route.getArrivalHubId());

  }

  @Test
  @DisplayName("허브경로 목록 조회")
  void getHubRouteList() {
    //given
    HubCreateCommand hubCreateCommand1 = new HubCreateCommand("CENTRAL", "경기남부", "도로명주소", "지번주소");
    HubCreateCommand hubCreateCommand2 = new HubCreateCommand("REGIONAL", "경기북부", "도로명주소", "지번주소");
    RouteCreateCommand command1 = new RouteCreateCommand(1L, 2L);
    RouteCreateCommand command2 = new RouteCreateCommand(2L, 3L);
    Hub mockHub = Hub.builder().build();
    Mockito.when(hubUseCase.existsHub(any())).thenReturn(true);
    //when
    Route route = routeService.createHubRoute(command1);
    Route route2 = routeService.createHubRoute(command2);
    RouteHistoryListResponse departHubName = routeService.getHubRouteList(0, 10, "departHubName", null);
    //then
    assertEquals(1L, route.getDepartHubId());
    assertEquals(2L, route.getArrivalHubId());
    assertEquals(2, departHubName.content().size());
  }

  @Test
  @DisplayName("허브경로 상세 조회")
  void getHubRouteDetails() {
    //given
    HubCreateCommand hubCreateCommand1 = new HubCreateCommand("CENTRAL", "경기남부", "도로명주소", "지번주소");
    RouteCreateCommand command = new RouteCreateCommand(1L, 2L);
    Hub mockHub = Hub.builder().build();
    DepartArrivalCommand departArrivalCommand = new DepartArrivalCommand("경기남부", "경기북부");
    Mockito.when(hubUseCase.existsHub(any())).thenReturn(true);
    Mockito.when(hubUseCase.getHubNameInfo(any(), any())).thenReturn(departArrivalCommand);
    //when
    Route route = routeService.createHubRoute(command);
    RouteDetailsResponse routeDetails = routeService.getRouteDetails(1L);

    //then
    assertEquals("경기남부", routeDetails.departHubName());
    assertEquals("경기북부", routeDetails.arrivalHubName());
    assertEquals(3000, routeDetails.distance());  //임시
    assertEquals(10000, routeDetails.duration());  //임시
  }

  @Test
  @DisplayName("허브경로 삭제")
  void deleteHubRoute() {

    //given
    HubCreateCommand hubCreateCommand1 = new HubCreateCommand("CENTRAL", "경기남부", "도로명주소", "지번주소");
    HubCreateCommand hubCreateCommand2 = new HubCreateCommand("REGIONAL", "경기북부", "도로명주소", "지번주소");
    RouteCreateCommand command = new RouteCreateCommand(1L, 2L);
    Hub mockHub = Hub.builder().build();
    Mockito.when(hubUseCase.existsHub(any())).thenReturn(true);
    Route route = routeService.createHubRoute(command);
    Mockito.when(routePersistencePort.findById(any())).thenReturn(Optional.of(route));
    //when

//    routeService.deleteHubRoute(route.getRouteId());

    //then
  }
}
