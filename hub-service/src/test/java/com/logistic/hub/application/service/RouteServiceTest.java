package com.logistic.hub.application.service;


/*

@SpringBootTest
@Transactional
class RouteServiceTest {

  @Autowired
  private RouteService routeService;
  @MockitoBean
  private HubUseCase hubUseCase;
  @MockitoBean
  private HubQueryUseCase queryUseCase;
  @Autowired
  private HubPersistencePort hubPersistencePort;
  @Autowired
  private RouteQueryService routeQueryService;

  @Test
  @DisplayName("허브경로 생성")
  void createHubRoute() {
    //given
    RouteCreateCommand command = new RouteCreateCommand(1L, 2L, null);
    Hub mockHub = Hub.builder().build();
    Mockito.when(queryUseCase.existsHub(any())).thenReturn(true);

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
    HubCreateCommand mockhubCreateCommand1 = new HubCreateCommand("CENTRAL", "경기남부", "도로명주소", "지번주소", null);
    HubCreateCommand mockhubCreateCommand2 = new HubCreateCommand("CENTRAL", "경기북부", "도로명주소", "지번주소", null);
    AddressCommand mockAddressCommand = new AddressCommand("도로명주소", "지번주소", 300.0, 70.0);
    Hub mockHub1 = Hub.createHub(mockhubCreateCommand1, mockAddressCommand);
    Hub mockHub2 = Hub.createHub(mockhubCreateCommand2, mockAddressCommand);
    Hub save1 = hubPersistencePort.save(mockHub1);
    Hub save2 = hubPersistencePort.save(mockHub2);

    RouteCreateCommand command = new RouteCreateCommand(save1.getId(), save2.getId(), null);
    Mockito.when(queryUseCase.existsHub(any())).thenReturn(true);
    Route route = routeService.createOrUpdateHubRoute(command);
    System.out.println(mockHub1.getId() + " " + mockHub2.getId());
    RouteSearchQuery query = new RouteSearchQuery(0, 10, "departHubName", null);
    //when
    Page<RouteHistoryDto> hubRouteList = routeQueryService.getHubRouteList(query);

    //then
    assertEquals(save1.getId(), route.getDepartHubId());
    assertEquals(save2.getId(), route.getArrivalHubId());
    assertEquals(1, hubRouteList.getSize());
  }

  @Test
  @DisplayName("허브경로 상세 조회")
  void getHubRouteDetails() {
    //given
    RouteCreateCommand command = new RouteCreateCommand(1L, 2L, null);
    Mockito.when(queryUseCase.existsHub(any())).thenReturn(true);
    Route route = routeService.createOrUpdateHubRoute(command);

    DepartArrivalDto departArrivalCommand = new DepartArrivalDto("경기남부", "경기북부");
    Mockito.when(hubUseCase.getHubNameInfo(any(), any())).thenReturn(departArrivalCommand);
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

  @Test
  @DisplayName("허브경로 삭제")
  void deleteHubRoute() {
    //given
    RouteCreateCommand command = new RouteCreateCommand(1L, 2L, null);
    Hub mockHub = Hub.builder().build();
    Mockito.when(queryUseCase.existsHub(any())).thenReturn(true);
    Route route = routeService.createOrUpdateHubRoute(command);
    RouteFindQuery query = new RouteFindQuery(route.getId());
    RouteDeleteCommand deleteCommand = new RouteDeleteCommand(route.getId(), null);
    //when
    routeService.deleteHubRoute(deleteCommand);
    //then
    assertThrows(RouteAlreadyDeletedException.class, () -> routeQueryService.getRouteDetails(query));
  }
}
*/
