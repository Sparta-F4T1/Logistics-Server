package com.logistic.product.adapter.in.external.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistic.common.annotation.Adapter;
import com.logistic.common.internal.message.CancelOrderEvent;
import com.logistic.common.internal.message.DeleteCompanyEvent;
import com.logistic.product.adapter.in.external.message.mapper.ProductEventMapper;
import com.logistic.product.application.port.in.ProductCommandUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Slf4j(topic = "ProductEventAdapter")
@Adapter
@RequiredArgsConstructor
public class ProductEventAdapter {
  private final ProductEventMapper mapper;
  private final ObjectMapper objectMapper;
  private final ProductCommandUseCase commandUseCase;

  @RabbitListener(queues = "order.product")
  public void cancelOrder(String event) throws JsonProcessingException {
    try {
      CancelOrderEvent cancelOrderEvent = objectMapper.readValue(event, CancelOrderEvent.class);
      commandUseCase.increaseStock(mapper.toUpdateStockCommand(cancelOrderEvent));
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }

  }

  @RabbitListener(queues = "delete.company.product")
  public void deleteCompany(DeleteCompanyEvent deleteCompanyEvent) {
    commandUseCase.deleteProduct(mapper.toDeleteProductCommand(deleteCompanyEvent));
  }

}
