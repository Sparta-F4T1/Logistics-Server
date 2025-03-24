package com.logistic.hub.application.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.logistic.common.passport.model.Passport;
import com.logistic.common.passport.model.UserInfo;
import com.logistic.hub.application.port.in.HubQueryUseCase;
import com.logistic.hub.application.port.in.command.HubCreateCommand;
import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.application.port.in.command.RouteDeleteCommand;
import com.logistic.hub.application.port.in.command.RouteInfoCommand;
import com.logistic.hub.application.port.in.query.RouteFindQuery;
import com.logistic.hub.application.port.in.query.RouteSearchQuery;
import com.logistic.hub.application.port.out.client.HubInternalPort;
import com.logistic.hub.application.port.out.persistence.HubPersistencePort;
import com.logistic.hub.application.service.dto.DepartArrivalDto;
import com.logistic.hub.application.service.dto.RouteDetailsDto;
import com.logistic.hub.application.service.dto.RouteHistoryDto;
import com.logistic.hub.domain.Hub;
import com.logistic.hub.domain.Route;
import com.logistic.hub.domain.command.AddressCommand;
import com.logistic.hub.domain.exception.RouteAlreadyDeletedException;
import com.logistic.hub.domain.vo.Address;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class RouteServiceTest {

  @Autowired
  private RouteService routeService;
  @Autowired
  private RouteQueryService routeQueryService;
  @MockitoBean
  private HubQueryUseCase hubQueryUseCase;
  @MockitoBean
  private HubInternalPort hubInternalPort;
  @Autowired
  private HubPersistencePort hubPersistencePort;

  static Passport passport;

  @BeforeAll
  static void setUp() {
    passport = new Passport();
    passport.setUserInfo(new UserInfo("test", "MASTER_ADMIN"));

  }

  @Test
  @DisplayName("허브경로 생성")
  void createHubRoute() {
    //given
    RouteCreateCommand command = new RouteCreateCommand(1L, 2L, passport);
    Address address = new Address("도로", "지번", 130.0, 70.0);
    Hub mockHub = Hub.builder().address(address).build();
    Mockito.when(hubQueryUseCase.existsHub(any())).thenReturn(true);
    RouteInfoCommand routeInfoCommand = new RouteInfoCommand(10, 100);
    when(hubInternalPort.getRouteInfo(any(), any())).thenReturn(routeInfoCommand);
    when(hubQueryUseCase.getHubDetails(any())).thenReturn(mockHub);
    //when
    Route route = routeService.createOrUpdateHubRoute(command);

    //then
    assertEquals(1L, route.getDepartHubId());
    assertEquals(2L, route.getArrivalHubId());
  }

  @Test
  @DisplayName("허브경로 삭제")
  void deleteHubRoute() {
    //given
    RouteCreateCommand command = new RouteCreateCommand(1L, 2L, passport);
    Address address = new Address("도로", "지번", 130.0, 70.0);
    Hub mockHub = Hub.builder().address(address).build();
    Mockito.when(hubQueryUseCase.existsHub(any())).thenReturn(true);
    when(hubQueryUseCase.getHubDetails(any())).thenReturn(mockHub);
    RouteInfoCommand routeInfoCommand = new RouteInfoCommand(10, 100);
    when(hubInternalPort.getRouteInfo(any(), any())).thenReturn(routeInfoCommand);
    Route route = routeService.createOrUpdateHubRoute(command);
    RouteFindQuery query = new RouteFindQuery(route.getId());
    RouteDeleteCommand deleteCommand = new RouteDeleteCommand(route.getId(), passport);
    //when
    routeService.deleteHubRoute(deleteCommand);
    //then
    assertThrows(RouteAlreadyDeletedException.class, () -> routeQueryService.getRouteDetails(query));
  }

  @Test
  @DisplayName("허브경로 목록 조회")
  void getHubRouteList() {
    //given
    HubCreateCommand mockhubCreateCommand1 = new HubCreateCommand("CENTRAL", "경기남부", "도로명주소", "지번주소", passport);
    HubCreateCommand mockhubCreateCommand2 = new HubCreateCommand("CENTRAL", "경기북부", "도로명주소", "지번주소", passport);
    AddressCommand mockAddressCommand = new AddressCommand("도로명주소", "지번주소", 300.0, 70.0);
    Hub mockHub1 = Hub.createHub(mockhubCreateCommand1, mockAddressCommand);
    Hub mockHub2 = Hub.createHub(mockhubCreateCommand2, mockAddressCommand);
    Hub save1 = hubPersistencePort.save(mockHub1);
    Hub save2 = hubPersistencePort.save(mockHub2);
    when(hubQueryUseCase.getHubDetails(any())).thenReturn(mockHub1);

    RouteInfoCommand routeInfoCommand = new RouteInfoCommand(10, 100);
    when(hubInternalPort.getRouteInfo(any(), any())).thenReturn(routeInfoCommand);
    RouteCreateCommand command = new RouteCreateCommand(save1.getId(), save2.getId(), passport);
    Mockito.when(hubQueryUseCase.existsHub(any())).thenReturn(true);
    Route route = routeService.createOrUpdateHubRoute(command);

    RouteSearchQuery query = new RouteSearchQuery(0, 10, "departHubName", null);
    //when
    Page<RouteHistoryDto> hubRouteList = routeQueryService.getHubRouteList(query);

    //then
    assertEquals(save1.getId(), route.getDepartHubId());
    assertEquals(save2.getId(), route.getArrivalHubId());
    assertEquals(1, hubRouteList.getTotalElements());
  }

  @Test
  @DisplayName("허브경로 상세 조회")
  void getHubRouteDetails() {

    //given
    RouteCreateCommand command = new RouteCreateCommand(1L, 2L, passport);
    Address address = new Address("도로", "지번", 130.0, 70.0);
    Hub mockHub = Hub.builder().address(address).build();
    Mockito.when(hubQueryUseCase.existsHub(any())).thenReturn(true);
    RouteInfoCommand routeInfoCommand = new RouteInfoCommand(10, 100);
    when(hubInternalPort.getRouteInfo(any(), any())).thenReturn(routeInfoCommand);
    when(hubQueryUseCase.getHubDetails(any())).thenReturn(mockHub);
    DepartArrivalDto departArrivalCommand = new DepartArrivalDto("경기남부", "경기북부");
    when(hubQueryUseCase.getHubNameInfo(any(), any())).thenReturn(departArrivalCommand);
    Route route = routeService.createOrUpdateHubRoute(command);

    RouteFindQuery query = new RouteFindQuery(1L);
    //when
    RouteDetailsDto routeDetails = routeQueryService.getRouteDetails(query);

    //then
    assertEquals(route.getId(), routeDetails.routeId());
    assertEquals(1L, route.getDepartHubId());
    assertEquals(2L, route.getArrivalHubId());
    assertEquals("경기남부", routeDetails.departHubName());
    assertEquals("경기북부", routeDetails.arrivalHubName());
  }


}
