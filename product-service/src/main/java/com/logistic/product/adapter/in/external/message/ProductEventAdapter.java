package com.logistic.product.adapter.in.external.message;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.message.CancelOrderEvent;
import com.logistic.common.internal.message.DeleteCompanyEvent;
import com.logistic.product.adapter.in.external.message.mapper.ProductEventMapper;
import com.logistic.product.application.port.in.ProductCommandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Adapter
@RequiredArgsConstructor
public class ProductEventAdapter {
  private final ProductEventMapper mapper;
  private final ProductCommandUseCase commandUseCase;

  @RabbitListener(queues = "order.product")
  public void cancelOrder(CancelOrderEvent cancelOrderEvent) {
    commandUseCase.increaseStock(mapper.toUpdateStockCommand(cancelOrderEvent));
  }

  @RabbitListener(queues = "delete.company.product")
  public void deleteCompany(DeleteCompanyEvent deleteCompanyEvent) {
    commandUseCase.deleteProduct(mapper.toDeleteProductCommand(deleteCompanyEvent));
  }

}
