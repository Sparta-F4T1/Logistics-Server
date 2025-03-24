package com.logistic.driver.domain.event;

import com.logistic.driver.domain.model.vo.Company;
import com.logistic.driver.domain.model.vo.Hub;
import java.util.List;
import java.util.Map;

public record DriverRouteNotificationEvent(
    Map<String, List<Company>> companyMap,
    Hub hub) {
}
