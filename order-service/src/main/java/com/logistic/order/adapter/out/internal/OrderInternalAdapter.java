package com.logistic.order.adapter.out.internal;

import com.logistic.common.annotation.Adapter;
import com.logistic.order.adapter.out.internal.client.ProductFeignClient;
import com.logistic.order.adapter.out.internal.mapper.OrderClientMapper;
import com.logistic.order.application.port.out.OrderInternalPort;
import com.logistic.order.domain.Order;
import com.logistic.order.domain.vo.OrderProduct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Adapter
@RequiredArgsConstructor
public class OrderInternalAdapter implements OrderInternalPort {
  private final ProductFeignClient productFeignClient;
  private final RabbitTemplate rabbitTemplate;
  private final OrderClientMapper orderClientMapper;

  public Map<Long, Integer> updateProductInventory(List<OrderProduct> orderProducts){
    Map<Long, Integer> request = orderProducts.stream()
        .collect(Collectors.toMap(OrderProduct::getProductId, OrderProduct::getQuantity));

    return productFeignClient.updateProductInventory(request);
  }

  public void sendCreateDelivery(Order order){
    rabbitTemplate.convertAndSend(orderClientMapper.toDeliveryCommand(order));
  }
}
