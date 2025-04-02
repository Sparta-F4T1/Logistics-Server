package com.logistic.driver.adapter.in.external.event.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistic.driver.domain.event.CompanyDeliveryEvent;
import com.logistic.driver.domain.event.DomainEventEnvelop;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

@Slf4j
@RequiredArgsConstructor
public class CompanyDriverEventDeserializer implements Deserializer<DomainEventEnvelop<CompanyDeliveryEvent>> {

  private final ObjectMapper objectMapper;

  @Override
  public DomainEventEnvelop<CompanyDeliveryEvent> deserialize(String topic, byte[] data) {
    try {
      return objectMapper.readValue(data, objectMapper.getTypeFactory()
          .constructParametricType(DomainEventEnvelop.class, CompanyDeliveryEvent.class));
    } catch (IOException e) {
      log.error("[CompanyDriverEventDeserializer] topic : {} , data : {}, error : ", topic, data, e);
      throw new RuntimeException(e);
    }
  }
}
