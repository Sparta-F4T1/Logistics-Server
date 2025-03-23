package com.logistic.hub.application.service;

import static java.util.Comparator.comparingInt;

import com.logistic.common.annotation.UseCase;
import com.logistic.hub.application.port.in.HubUseCase;
import com.logistic.hub.application.port.in.RouteQueryUseCase;
import com.logistic.hub.application.port.in.command.DepartArrivalIdCommand;
import com.logistic.hub.application.port.in.query.RouteFindQuery;
import com.logistic.hub.application.port.in.query.RouteListQuery;
import com.logistic.hub.application.port.in.query.RouteSearchQuery;
import com.logistic.hub.application.port.out.persistence.RoutePersistencePort;
import com.logistic.hub.application.service.dto.DepartArrivalDto;
import com.logistic.hub.application.service.dto.RouteDetailsDto;
import com.logistic.hub.application.service.dto.RouteHistoryDto;
import com.logistic.hub.config.RestPage;
import com.logistic.hub.domain.Route;
import com.logistic.hub.domain.exception.RouteAlreadyDeletedException;
import com.logistic.hub.domain.exception.RouteCalculateFailedException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RouteQueryService implements RouteQueryUseCase {
  private final RoutePersistencePort routePersistencePort;
  private final HubUseCase hubUseCase;

  @Override
  @Cacheable(cacheNames = "routeList", key = "{#routeSearchQuery.page(),#routeSearchQuery.size(),#routeSearchQuery.searchType(), #routeSearchQuery.search()}")
  public RestPage<RouteHistoryDto> getHubRouteList(RouteSearchQuery routeSearchQuery) {
    Sort.Direction direction = Direction.ASC;  // 오름차순

    Sort sort1 = Sort.by(direction, "Id"); //정렬기준
    Pageable pageable = PageRequest.of(routeSearchQuery.page(), routeSearchQuery.size(), sort1);

    Page<RouteHistoryDto> list = routePersistencePort.findAllBySearch(routeSearchQuery.searchType(),
        routeSearchQuery.search(), pageable);
    return new RestPage<>(list);
  }

  @Override
  public RouteDetailsDto getRouteDetails(RouteFindQuery routeFindQuery) {
    Route route = routePersistencePort.findById(routeFindQuery.routeId());

    isDeleted(route);
    DepartArrivalDto command = hubUseCase.getHubNameInfo(route.getDepartHubId(), route.getArrivalHubId());
    RouteDetailsDto routeDetails = RouteDetailsDto.from(route, command.departHubName(),
        command.arrivalHubName());
    return routeDetails;
  }

  @Override
  public Route findRoute(RouteFindQuery routeFindQuery) {
    Route route = routePersistencePort.findById(routeFindQuery.routeId());

    isDeleted(route);

    return route;
  }

  @Override
  public List<Route> findRouteList(RouteListQuery routeListQuery) {
    List<Route> routeList = routePersistencePort.findAll();

    return routeList;
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

  private void isDeleted(Route route) {
    if (route.getIsDeleted()) {
      throw new RouteAlreadyDeletedException("이미 삭제된 허브입니다.");
    }
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
