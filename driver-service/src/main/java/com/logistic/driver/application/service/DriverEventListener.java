package com.logistic.driver.application.service;

import com.logistic.driver.application.port.out.DriverInternalPort;
import com.logistic.driver.application.port.out.DriverMessagePort;
import com.logistic.driver.application.port.out.optimization.DriverTspPort;
import com.logistic.driver.domain.event.AssignedDriverToNotificationEvent;
import com.logistic.driver.domain.event.DriverRouteNotificationEvent;
import com.logistic.driver.domain.model.vo.Company;
import com.logistic.driver.domain.model.vo.Direction;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class DriverEventListener {
  private final DriverTspPort tspPort;
  private final DriverMessagePort messagePort;
  private final DriverInternalPort internalPort;

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void companyDriverNotificationEvent(final DriverRouteNotificationEvent event) {
    event.companyMap().forEach((key, value) -> {
      List<Company> tspResult = tspPort.tryTsp(value, event.hub());
      List<Direction> directions = internalPort.getDirections(tspResult);
      AssignedDriverToNotificationEvent notificationEvent =
          new AssignedDriverToNotificationEvent(key, directions);
      messagePort.sendToNotify(notificationEvent);
    });
  }
}
