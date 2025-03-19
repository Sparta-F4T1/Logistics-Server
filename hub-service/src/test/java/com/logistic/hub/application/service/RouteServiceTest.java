package com.logistic.hub.application.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import com.logistic.hub.adapter.in.web.response.RouteDetailsResponse;
import com.logistic.hub.adapter.in.web.response.RouteHistoryListResponse;
import com.logistic.hub.application.port.in.HubUseCase;
import com.logistic.hub.application.port.in.command.DepartArrivalCommand;
import com.logistic.hub.application.port.in.command.HubCreateCommand;
import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.application.port.out.persistence.HubPersistencePort;
import com.logistic.hub.domain.Hub;
import com.logistic.hub.domain.Route;
import com.logistic.hub.domain.command.AddressCommand;
import com.logistic.hub.domain.exception.RouteAlreadyDeletedException;
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
  private HubUseCase hubUseCase;
  @Autowired
  private HubPersistencePort hubPersistencePort;

  @Test
  @DisplayName("허브경로 생성")
  void createHubRoute() {
    //given
    RouteCreateCommand command = new RouteCreateCommand(1L, 2L);
    Hub mockHub = Hub.builder().build();
    Mockito.when(hubUseCase.existsHub(any())).thenReturn(true);

    //when
    Route route = routeService.createOrUpdateHubRoute(command);

    //then
    assertEquals(1L, route.getDepartHubId());
    assertEquals(2L, route.getArrivalHubId());
  }

  @Test
  @DisplayName("허브경로 목록 조회")
  void getHubRouteList() {
    //given
    HubCreateCommand mockhubCreateCommand1 = new HubCreateCommand("CENTRAL", "경기남부", "도로명주소", "지번주소");
    HubCreateCommand mockhubCreateCommand2 = new HubCreateCommand("CENTRAL", "경기북부", "도로명주소", "지번주소");
    AddressCommand mockAddressCommand = new AddressCommand("도로명주소", "지번주소", 300.0, 70.0);
    Hub mockHub1 = Hub.createHub(mockhubCreateCommand1, mockAddressCommand);
    Hub mockHub2 = Hub.createHub(mockhubCreateCommand2, mockAddressCommand);
    Hub save1 = hubPersistencePort.save(mockHub1);
    Hub save2 = hubPersistencePort.save(mockHub2);

    RouteCreateCommand command = new RouteCreateCommand(save1.getId(), save2.getId());
    Mockito.when(hubUseCase.existsHub(any())).thenReturn(true);
    Route route = routeService.createOrUpdateHubRoute(command);
    System.out.println(mockHub1.getId() + " " + mockHub2.getId());
    //when
    RouteHistoryListResponse departHubName = routeService.getHubRouteList(0, 10, "departHubName", null);

    //then
    assertEquals(save1.getId(), route.getDepartHubId());
    assertEquals(save2.getId(), route.getArrivalHubId());
    assertEquals(1, departHubName.content().size());
  }

  @Test
  @DisplayName("허브경로 상세 조회")
  void getHubRouteDetails() {
    //given
    RouteCreateCommand command = new RouteCreateCommand(1L, 2L);
    Mockito.when(hubUseCase.existsHub(any())).thenReturn(true);
    Route route = routeService.createOrUpdateHubRoute(command);

    DepartArrivalCommand departArrivalCommand = new DepartArrivalCommand("경기남부", "경기북부");
    Mockito.when(hubUseCase.getHubNameInfo(any(), any())).thenReturn(departArrivalCommand);
    //when
    RouteDetailsResponse routeDetails = routeService.getRouteDetails(1L);

    //then
    assertEquals(route.getId(), routeDetails.hubRouteId());
    assertEquals(1L, route.getDepartHubId());
    assertEquals(2L, route.getArrivalHubId());
    assertEquals("경기남부", routeDetails.departHubName());
    assertEquals("경기북부", routeDetails.arrivalHubName());
  }

  @Test
  @DisplayName("허브경로 삭제")
  void deleteHubRoute() {
    //given
    RouteCreateCommand command = new RouteCreateCommand(1L, 2L);
    Hub mockHub = Hub.builder().build();
    Mockito.when(hubUseCase.existsHub(any())).thenReturn(true);
    Route route = routeService.createOrUpdateHubRoute(command);
    //when
    routeService.deleteHubRoute(route.getId());
    //then
    assertThrows(RouteAlreadyDeletedException.class, () -> routeService.getRouteDetails(route.getId()));
  }
}
