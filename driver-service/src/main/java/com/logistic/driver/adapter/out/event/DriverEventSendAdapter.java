package com.logistic.driver.adapter.out.event;

import com.logistic.common.annotation.Adapter;
import com.logistic.driver.application.port.out.DriverEventSendPort;
import com.logistic.driver.domain.event.CompanyDeliveryEvent;
import com.logistic.driver.domain.event.DomainEventEnvelop;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
@RequiredArgsConstructor
public class DriverEventSendAdapter implements DriverEventSendPort {
  private final CompanyDeliveryEventPublisher companyDeliveryEventPublisher;

  @Override
  public DomainEventEnvelop<CompanyDeliveryEvent> sendMessage(DomainEventEnvelop<CompanyDeliveryEvent> message) {
    log.info("[CompanyDeliverySendAdapter] [sendMessage] message ::: {}", message);
    companyDeliveryEventPublisher.publish(message);
    return message;
  }
}
