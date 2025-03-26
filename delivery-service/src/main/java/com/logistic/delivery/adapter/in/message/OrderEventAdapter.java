package com.logistic.delivery.adapter.in.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.message.CreateOrderEvent;
import com.logistic.delivery.adapter.in.message.mapper.DeliveryEventMapper;
import com.logistic.delivery.application.port.in.DeliveryUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Adapter
@Slf4j(topic = "OrderEventAdapter")
@RequiredArgsConstructor
public class OrderEventAdapter {
  private final ObjectMapper objectMapper;
  private final DeliveryUseCase deliveryUseCase;
  private final DeliveryEventMapper deliveryEventMapper;

  @RabbitListener(queues = "order.delivery")
  public void receiveOrderEvent(String message) throws JsonProcessingException {
    try {
      CreateOrderEvent event = objectMapper.readValue(message, CreateOrderEvent.class);
      deliveryUseCase.createDelivery(deliveryEventMapper.toCreateCommand(event));
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
    } catch (Exception e) {
      log.error("주문생성 이벤트 처리 중 예외 발생:{}", e.getMessage());
    }
  }
}
