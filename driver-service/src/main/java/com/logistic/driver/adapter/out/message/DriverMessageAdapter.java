package com.logistic.driver.adapter.out.message;

import com.logistic.common.annotation.Adapter;
import com.logistic.driver.application.port.out.DriverMessagePort;
import com.logistic.driver.domain.event.AssignedDriverToDeliveryEvent;
import com.logistic.driver.domain.event.AssignedDriverToNotificationEvent;
import com.logistic.driver.domain.model.vo.Company;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Adapter
@RequiredArgsConstructor
public class DriverMessageAdapter implements DriverMessagePort {
  private final RabbitTemplate rabbitTemplate;

  @Override
  public void sendCompanyDriver(final Map<String, List<Company>> companyMap) {
    companyMap.forEach((companyId, value) -> {
      List<Long> companyList = value.stream().map(Company::companyId).toList();
      AssignedDriverToDeliveryEvent event = new AssignedDriverToDeliveryEvent(companyId, companyList);
      rabbitTemplate.convertAndSend(event);
    });
  }

  @Override
  public void sendToNotify(final AssignedDriverToNotificationEvent notificationEvent) {
    rabbitTemplate.convertAndSend(notificationEvent);
  }
}
