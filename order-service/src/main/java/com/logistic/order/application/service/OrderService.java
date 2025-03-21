package com.logistic.order.application.service;

import com.logistic.order.application.port.OrderPersistencePort;
import com.logistic.order.application.port.in.OrderUseCase;
import com.logistic.order.application.port.in.command.CreateOrderCommand;
import com.logistic.order.domain.Order;
import com.logistic.order.domain.OrderStatus;
import com.logistic.order.domain.vo.OrderProduct;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {

  private final OrderPersistencePort orderPersistencePort;

  @Override
  public Order createOrder(CreateOrderCommand command) {
    Order order = Order.create(
        command.sellerId(),
        command.buyerId(),
        command.memo(),
        command.orderProducts().stream()
            .map(orderProduct -> OrderProduct.create(orderProduct.productId(), orderProduct.quantity()))
            .collect(Collectors.toList())
    );
    return orderPersistencePort.save(order);
  }

  @Override
  public Order updateOrder(Long orderId, OrderStatus orderStatus) {
    Order order = orderPersistencePort.findById(orderId);
    order.updateStatus(orderStatus);
    return orderPersistencePort.save(order);
  }

  @Override
  public void deleteOrder(Long orderId, String userId) {
    orderPersistencePort.delete(orderId, userId);
  }

  @Override
  public Order findOrder(Long orderId) {
    return orderPersistencePort.findById(orderId);
  }
}
