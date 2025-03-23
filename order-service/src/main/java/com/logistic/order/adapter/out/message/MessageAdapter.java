package com.logistic.order.adapter.out.message;

import com.logistic.common.annotation.Adapter;
import com.logistic.order.adapter.out.internal.mapper.OrderClientMapper;
import com.logistic.order.application.port.out.MessagePort;
import com.logistic.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Adapter
@RequiredArgsConstructor
public class MessageAdapter implements MessagePort {

  private final RabbitTemplate rabbitTemplate;
  private final OrderClientMapper orderClientMapper;

  @Override
  public void sendCreateDelivery(Order order) {
    rabbitTemplate.convertAndSend(orderClientMapper.toDeliveryCommand(order));
  }
}
