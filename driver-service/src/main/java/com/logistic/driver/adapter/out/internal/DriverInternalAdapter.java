package com.logistic.driver.adapter.out.internal;

import com.logistic.common.internal.response.GpsClientResponse;
import com.logistic.driver.adapter.out.internal.client.CompanyFeignClient;
import com.logistic.driver.adapter.out.internal.client.GpsFeignClient;
import com.logistic.driver.adapter.out.internal.client.HubFeignClient;
import com.logistic.driver.adapter.out.internal.client.UserFeignClient;
import com.logistic.driver.adapter.out.internal.mapper.DriverClientMapper;
import com.logistic.driver.application.port.out.DriverInternalPort;
import com.logistic.driver.domain.exception.CustomNotFoundException.GpsNotFoundException;
import com.logistic.driver.domain.exception.CustomNotFoundException.HubNotFoundException;
import com.logistic.driver.domain.exception.CustomNotFoundException.UserNotFoundException;
import com.logistic.driver.domain.model.vo.Company;
import com.logistic.driver.domain.model.vo.Direction;
import com.logistic.driver.domain.model.vo.Hub;
import com.logistic.driver.domain.model.vo.User;
import feign.FeignException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DriverInternalAdapter implements DriverInternalPort {
  private final DriverClientMapper mapper;
  private final GpsFeignClient gpsFeignClient;
  private final HubFeignClient hubFeignClient;
  private final UserFeignClient userFeignClient;
  private final CompanyFeignClient companyFeignClient;

  @Override
  public Hub findHub(final Long hubId) {
    try {
      return mapper.toHub(hubFeignClient.findHub(hubId));
    } catch (FeignException e) {
      throw new HubNotFoundException();
    }
  }

  @Override
  public List<Hub> findHubList(final List<Long> hubIds) {
    try {
      return hubFeignClient.findHubList(hubIds).stream().map(mapper::toHub).toList();
    } catch (FeignException e) {
      throw new HubNotFoundException();
    }
  }

  @Override
  public User findUser(final String userId) {
    try {
      return mapper.toUser(userFeignClient.findUser(userId));
    } catch (FeignException e) {
      throw new UserNotFoundException();
    }
  }

  @Override
  public List<Company> findCompanyList(final List<Long> companyIds) {
    try {
      return companyFeignClient.findCompanyList(companyIds).stream().map(mapper::toCompany).toList();
    } catch (FeignException e) {
      throw new HubNotFoundException();
    }
  }

  @Override
  public Map<Long, Hub> findHubMap(final List<Long> hubIds) {
    try {
      List<Hub> hubList = hubFeignClient.findHubList(hubIds).stream().map(mapper::toHub).toList();
      Map<Long, Hub> hubMap = new HashMap<>();
      for (Hub hub : hubList) {
        hubMap.put(hub.hubId(), hub);
      }
      return hubMap;
    } catch (FeignException e) {
      throw new HubNotFoundException();
    }
  }

  @Override
  public List<Direction> getDirections(final List<Company> companyList) {
    try {
      List<Direction> directions = new ArrayList<>();
      for (int i = 0; i < companyList.size() - 1; i++) {
        Company departCompany = companyList.get(i);
        Company arrivalCompany = companyList.get(i + 1);
        String depart = getCoordinates(departCompany);
        String arrival = getCoordinates(arrivalCompany);
        GpsClientResponse response = gpsFeignClient.findDistanceAndDuration(depart, arrival);
        Direction direction = new Direction(departCompany.companyId(), arrivalCompany.companyId(), response.distance(),
            response.duration());
        directions.add(direction);
      }
      return directions;
    } catch (FeignException e) {
      throw new GpsNotFoundException();
    }
  }

  private String getCoordinates(Company company) {
    return company.latitude() + "," + company.longitude();
  }
}
