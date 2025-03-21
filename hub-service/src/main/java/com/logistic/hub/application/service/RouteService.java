package com.logistic.hub.application.service;

import static java.util.Comparator.comparingInt;

import com.logistic.common.annotation.UseCase;
import com.logistic.hub.adapter.in.web.response.RouteDetailsResponse;
import com.logistic.hub.adapter.in.web.response.RouteHistoryListResponse;
import com.logistic.hub.adapter.in.web.response.RouteHistoryResponse;
import com.logistic.hub.application.port.in.HubUseCase;
import com.logistic.hub.application.port.in.RouteUseCase;
import com.logistic.hub.application.port.in.command.DepartArrivalCommand;
import com.logistic.hub.application.port.in.command.DepartArrivalIdCommand;
import com.logistic.hub.application.port.in.command.RouteCreateCommand;
import com.logistic.hub.application.port.in.command.RouteInfoCommand;
import com.logistic.hub.application.port.out.client.GpsInternalPort;
import com.logistic.hub.application.port.out.persistence.RoutePersistencePort;
import com.logistic.hub.domain.Route;
import com.logistic.hub.domain.exception.HubNotFoundException;
import com.logistic.hub.domain.exception.HubSameSelectionException;
import com.logistic.hub.domain.exception.RouteAlreadyDeletedException;
import com.logistic.hub.domain.exception.RouteCalculateFailedException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RouteService implements RouteUseCase {
  private final RoutePersistencePort routePersistencePort;
  private final HubUseCase hubUseCase;
  private final GpsInternalPort gpsInternalPort;

  @Override
  @Caching(evict = {
      @CacheEvict(cacheNames = "routeList", allEntries = true),
      @CacheEvict(cacheNames = "shorestPath", allEntries = true)
  })
  public Route createOrUpdateHubRoute(RouteCreateCommand routeCommand) {
    RouteInfoCommand routeInfoCommand = gpsInternalPort.getRouteInfo(routeCommand); //임시 ( 좌표정보 보내는걸로 구현 예상)
    Long departHubId = routeCommand.departHubId();
    Long arrivalHubId = routeCommand.arrivalHubId();
    if (departHubId.equals(arrivalHubId)) {
      throw new HubSameSelectionException("출발 허브와 도착 허브는 같을 수 없습니다");
    }
    if (!hubUseCase.existsHub(departHubId) || !hubUseCase.existsHub(departHubId)) {
      throw new HubNotFoundException("출발 혹은 도착 허브가 존재하지 않습니다");
    }
    Optional<Route> existRoute = routePersistencePort.findByDepartAndArrival(departHubId, arrivalHubId);
    Route route;
    if (existRoute.isPresent()) {
      route = existRoute.get();
      route.update(routeInfoCommand);
    } else {
      route = Route.createRoute(routeCommand, routeInfoCommand);
    }
    return routePersistencePort.save(route);
  }

  @Override
  @Cacheable(cacheNames = "routeList", key = "search")
  public RouteHistoryListResponse getHubRouteList(int page, int size, String orderBy, String search) {
    Sort.Direction direction = Direction.ASC;  // 오름차순
    Sort sort1 = Sort.by(direction, orderBy); //정렬기준
    Pageable pageable = PageRequest.of(page, size, sort1);

    Page<RouteHistoryResponse> list = routePersistencePort.findAllBySearch(search, pageable);
    RouteHistoryListResponse routeList = RouteHistoryListResponse.from(list);
    return routeList;
  }

  @Override
  public RouteDetailsResponse getRouteDetails(Long hubRouteId) {
    Route route = getOrElseThrow(hubRouteId);

    isDeleted(route);
    DepartArrivalCommand command = hubUseCase.getHubNameInfo(route.getDepartHubId(), route.getArrivalHubId());
    RouteDetailsResponse routeDetails = RouteDetailsResponse.from(route, command.departHubName(),
        command.arrivalHubName());
    return routeDetails;
  }

  private Route getOrElseThrow(Long hubRouteId) {
    return routePersistencePort.findById(hubRouteId);
  }

  @Override
  @CacheEvict(cacheNames = "routeList", allEntries = true)
  public void deleteHubRoute(Long hubRouteId) {
    Route route = getOrElseThrow(hubRouteId);
    isDeleted(route);
    routePersistencePort.delete(route);
  }

  private void isDeleted(Route route) {
    if (route.getIsDeleted()) {
      throw new RouteAlreadyDeletedException("이미 삭제된 허브입니다.");
    }
  }

  @Override
  @Cacheable(cacheNames = "shortestPath", key = "{ #command.departHubId(),#command.arrivalHubId() }")
  public List<Route> getShortestPath(DepartArrivalIdCommand command) {
    List<Route> shortestPath = new ArrayList<>(); //반환할 최단 경로
    Map<Long, Route> previousPath = new HashMap<>(); //최단 경로 저장되어 있음 (각 허브id가 목적지인 route map);

    Long departHubId = command.departHubId(); //출발 허브id
    Long arrival = command.arrivalHubId();  //목적 허브id

    List<Route> routeList = getAllRoutes();   //db에 저장되어있는 모든 route

    PriorityQueue<Node> queue = new PriorityQueue<>(comparingInt(o -> o.distance));

    Map<Long, List<Route>> graph = initGraph(routeList);
    Map<Long, Integer> weight = initWeight(routeList); ///거리로 판단

    weight.put(departHubId, 0); //시작점 거리 초기화
    queue.add(new Node(departHubId, 0, 0)); //출발지 queue에 추가

    while (!queue.isEmpty()) {
      Node currentNode = queue.poll();
      List<Route> routes = graph.get(currentNode.num);

      if (currentNode.num == arrival) {
        break;
      }
      for (Route route : routes) {    //인접 노드
        Long arrivalHubId = route.getArrivalHubId();
        int newDistance = weight.get(currentNode.num) + route.getDistance();
        if (weight.get(arrivalHubId) > newDistance) {
          weight.put(arrivalHubId, newDistance);
          queue.add(new Node(arrivalHubId, newDistance, currentNode.duration + route.getDuration()));
          previousPath.put(arrivalHubId, route);
        }

      }
    }
    if (previousPath.isEmpty() || previousPath.get(arrival) == null) {
      throw new RouteCalculateFailedException("최단경로를 찾을 수 없습니다.");
    }

    Long currentId = arrival;
    while (previousPath.containsKey(currentId)) {

      Route route = previousPath.get(currentId);
      shortestPath.add(route);
      currentId = route.getDepartHubId();
    }
    Collections.reverse(shortestPath);

    return shortestPath;
  }

  @Cacheable(cacheNames = "AllRouteList")
  public List<Route> getAllRoutes() {
    return routePersistencePort.findAll();
  }

  private Map<Long, Integer> initWeight(List<Route> routeList) {
    Map<Long, Integer> weight = new HashMap<>();
    for (Route route : routeList) {
      weight.put(route.getDepartHubId(), Integer.MAX_VALUE);
      weight.put(route.getArrivalHubId(), Integer.MAX_VALUE);
    }
    return weight;
  }

  private Map<Long, List<Route>> initGraph(List<Route> routeList) {
    Map<Long, List<Route>> graph = new HashMap<>();
    for (Route route : routeList) {
      graph.putIfAbsent(route.getDepartHubId(), new ArrayList<>());
      graph.putIfAbsent(route.getArrivalHubId(), new ArrayList<>());
      graph.get(route.getDepartHubId()).add(route);
    }
    return graph;
  }

  private static class Node {
    Long num;
    int distance;
    int duration;

    public Node(Long num, int distance, int duration) {
      this.num = num;
      this.distance = distance;
      this.duration = duration;
    }
  }
}