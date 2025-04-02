package com.logistic.driver.adapter.out.event;

import com.logistic.driver.domain.event.CompanyDeliveryEvent;
import com.logistic.driver.domain.event.DomainEvent;
import com.logistic.driver.domain.event.DomainEventEnvelop;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CompanyDeliveryEventPublisher {

  private final KafkaTemplate<String, DomainEventEnvelop<? extends DomainEvent>> kafkaTemplate;

  @Value("${spring.kafka.consumer.topic.hub-arrived}")
  private String hubArrivedTopic;

  public void publish(DomainEventEnvelop<CompanyDeliveryEvent> eventEnvelop) {
    CompletableFuture<SendResult<String, DomainEventEnvelop<? extends DomainEvent>>> result
        = kafkaTemplate.send(hubArrivedTopic, eventEnvelop);
    log.info("[CompanyDeliveryEventPublisher] [publish] eventType ::: {}, result ::: {}", eventEnvelop.getEventType(),
        result.isDone());
  }
}
