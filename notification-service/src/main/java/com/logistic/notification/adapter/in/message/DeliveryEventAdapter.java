package com.logistic.notification.adapter.in.message;

import com.logistic.common.annotation.Adapter;
import com.logistic.notification.adapter.in.message.mapper.NotificationClientMapper;
import com.logistic.notification.application.port.in.NotificationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Adapter
@RequiredArgsConstructor
public class DeliveryEventAdapter {

  private final NotificationUseCase useCase;
  private final NotificationClientMapper mapper;

  @RabbitListener(queues = "${rabbitmq.order.queue.name}")
  public void receiveDeliveryEvent(CreateDeliveryEvent event) {
    useCase.createSlackMessage(mapper.toCommand(event));
  }
}
