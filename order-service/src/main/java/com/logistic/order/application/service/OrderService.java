package com.logistic.order.application.service;

import com.logistic.order.application.port.OrderPersistencePort;
import com.logistic.order.application.port.in.OrderUseCase;
import com.logistic.order.application.port.in.command.OrderCreateCommand;
import com.logistic.order.domain.Order;
import com.logistic.order.domain.OrderProduct;
import com.logistic.order.domain.UserContext;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService implements OrderUseCase {

  private final OrderPersistencePort orderPersistencePort;

  public Order createOrder(OrderCreateCommand command){
    Order order = Order.create(
        command.sellerId(),
        command.buyerId(),
        command.memo(),
        command.orderProducts().stream()
            .map(orderProduct -> OrderProduct.create(orderProduct.productId(), orderProduct.amount()))
            .collect(Collectors.toList())
    );
    return orderPersistencePort.save(order);
  }
}
