package com.logistic.driver.application.port.out;

import com.logistic.driver.domain.model.vo.Hub;
import com.logistic.driver.domain.model.vo.User;
import java.util.List;

public interface DriverInternalPort {
  Hub findHub(Long hubId);

  List<Hub> findHubList(List<Long> hubId);

  User findUser(String userId);
}
