package com.logistic.driver.application.port.out;

import com.logistic.driver.domain.event.AssignedDriverToNotificationEvent;
import com.logistic.driver.domain.model.vo.Company;
import java.util.List;
import java.util.Map;

public interface DriverMessagePort {
  void sendCompanyDriver(Map<String, List<Company>> companyMap);

  void sendToNotify(AssignedDriverToNotificationEvent notificationEvent);
}
