package com.logistic.order.application.service;

import com.logistic.common.passport.model.RoleType;
import com.logistic.common.passport.model.UserInfo;
import com.logistic.order.application.port.OrderPersistencePort;
import com.logistic.order.application.port.in.OrderUseCase;
import com.logistic.order.application.port.in.command.CreateOrderCommand;
import com.logistic.order.application.port.out.MessagePort;
import com.logistic.order.application.port.out.OrderInternalPort;
import com.logistic.order.domain.Order;
import com.logistic.order.domain.OrderException.OrderBuyerNotAuthorized;
import com.logistic.order.domain.OrderStatus;
import com.logistic.order.domain.vo.OrderProduct;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {

  private final OrderPersistencePort orderPersistencePort;
  private final OrderInternalPort orderInternalPort;
  private final MessagePort messagePort;

  @Override
  public Order createOrder(CreateOrderCommand command) {
    checkCompanyManager(command.buyerId(), command.userInfo());

    List<OrderProduct> orderProducts = command.orderProducts().stream()
        .map(orderProduct -> OrderProduct.create(orderProduct.productId(), orderProduct.quantity()))
        .collect(Collectors.toList());

    Order order = Order.create(
        command.sellerId(),
        command.buyerId(),
        command.memo(),
        checkStock(orderProducts),
        orderProducts
    );

    order = orderPersistencePort.save(order);

    if (order.getStatus() == OrderStatus.IN_DELIVERY){
      messagePort.sendCreateOrder(order);
    }

    return order;
  }

  @Override
  public Order updateOrder(Long orderId, OrderStatus orderStatus) {
    Order order = orderPersistencePort.findById(orderId);
    order.updateStatus(orderStatus);

    if (orderStatus == OrderStatus.CANCELED){
      order.getOrderProducts()
          .forEach(OrderProduct::cancelStock);

      messagePort.sendCreateOrder(order);
    }

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

  private OrderStatus checkStock(List<OrderProduct> orderProducts){
    try{
      orderInternalPort.updateStock(orderProducts);
    }catch (Exception e){
      return OrderStatus.PENDING;
    }

    return OrderStatus.IN_DELIVERY;
  }

  private void checkCompanyManager(Long buyerId, UserInfo userInfo) {
    if(!userInfo.getRole().equals(String.valueOf(RoleType.COMPANY_PERSONNEL))) {
      throw new OrderBuyerNotAuthorized();
    }

    orderInternalPort.findCompany(buyerId)
        .userIds()
        .stream()
        .filter(userId -> userId.equals(userInfo.getUserId()))
        .findFirst()
        .orElseThrow(OrderBuyerNotAuthorized::new);
  }
}
