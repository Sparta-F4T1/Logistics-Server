package com.logistic.driver.application.port.out;

import com.logistic.driver.domain.event.CompanyDeliveryEvent;
import com.logistic.driver.domain.event.DomainEventEnvelop;

public interface DriverEventSendPort {
  DomainEventEnvelop<CompanyDeliveryEvent> sendMessage(DomainEventEnvelop<CompanyDeliveryEvent> envelop);
}
