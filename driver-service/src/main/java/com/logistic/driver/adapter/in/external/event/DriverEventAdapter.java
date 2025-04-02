package com.logistic.driver.adapter.in.external.event;

import com.logistic.common.annotation.Adapter;
import com.logistic.driver.adapter.in.external.event.mapper.DriverEventMapper;
import com.logistic.driver.application.port.in.DriverCommandUseCase;
import com.logistic.driver.domain.event.CompanyDeliveryEvent;
import com.logistic.driver.domain.event.DomainEventEnvelop;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@Adapter
@RequiredArgsConstructor
public class DriverEventAdapter {
  private final DriverEventMapper mapper;
  private final DriverCommandUseCase commandUseCase;

  @KafkaListener(
      groupId = "${spring.kafka.consumer.group-id}",
      topics = "${spring.kafka.consumer.topic.hub-arrived}",
      containerFactory = "companyDeliveryEventListener"
  )
  public void consume(DomainEventEnvelop<CompanyDeliveryEvent> envelop) {
    log.info("[[DriverEventAdapter] consume envelop: {}", envelop);
    try {
      commandUseCase.assignCompanyDrivers(mapper.toCommand(envelop.getEvent()));
    } catch (Exception e) {
      log.error("[DriverEventAdapter] envelop : {}, error :", envelop, e);
    }
  }
}
