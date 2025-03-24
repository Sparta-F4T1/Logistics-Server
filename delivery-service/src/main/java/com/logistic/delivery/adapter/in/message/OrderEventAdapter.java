package com.logistic.delivery.adapter.in.message;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.message.CreateOrderEvent;
import com.logistic.delivery.adapter.in.message.mapper.DeliveryEventMapper;
import com.logistic.delivery.application.port.in.DeliveryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Adapter
@RequiredArgsConstructor
public class OrderEventAdapter {

  private final DeliveryUseCase deliveryUseCase;
  private final DeliveryEventMapper deliveryEventMapper;

  @RabbitListener(queues = "${rabbitmq.order.queue.name}")
  public void receiveOrderEvent(CreateOrderEvent event) {
    deliveryUseCase.createDelivery(deliveryEventMapper.toCreateCommand(event));
  }
}
