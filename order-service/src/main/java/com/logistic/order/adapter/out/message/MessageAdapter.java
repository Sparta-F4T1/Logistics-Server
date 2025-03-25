package com.logistic.order.adapter.out.message;

import com.logistic.common.annotation.Adapter;
import com.logistic.order.adapter.out.internal.mapper.OrderClientMapper;
import com.logistic.order.application.port.out.MessagePort;
import com.logistic.order.domain.Order;
import com.logistic.order.domain.vo.OrderProduct;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

@Adapter
@RequiredArgsConstructor
public class MessageAdapter implements MessagePort {

  private final RabbitTemplate rabbitTemplate;
  private final OrderClientMapper orderClientMapper;

  @Value("${message.queue.delivery}")
  private String queueDelivery;

  @Value("${message.queue.product}")
  private String queueProduct;

  @Override
  public void sendCreateOrder(Order order, String slackEmail) {
    rabbitTemplate.convertAndSend(queueDelivery, orderClientMapper.toCreateOrderEvent(order, slackEmail));
  }

  @Override
  public void sendCancelOrder(List<OrderProduct> orderProducts) {
    rabbitTemplate.convertAndSend(queueProduct, orderClientMapper.toCancelOrderEvent(orderProducts.stream()
        .collect(Collectors.toMap(OrderProduct::getProductId, OrderProduct::getQuantity))));
  }
}
