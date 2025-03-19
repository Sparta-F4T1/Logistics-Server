package com.logistic.driver.application.port.out;

import com.logistic.common.passport.model.Passport;
import com.logistic.driver.application.service.dto.HubInfo;
import java.util.List;

public interface DriverInternalPort {
  HubInfo findHub(Long hubId, Passport passport);

  List<HubInfo> findHubList(List<Long> hubId, Passport passport);

}
