package com.logistic.driver.domain.event;

import com.logistic.driver.domain.model.vo.Direction;
import java.util.List;

public record AssignedDriverToNotificationEvent(
    String driverId,
    List<Direction> directions) {

}
