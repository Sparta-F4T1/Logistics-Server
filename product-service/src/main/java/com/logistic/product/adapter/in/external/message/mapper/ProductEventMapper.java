package com.logistic.product.adapter.in.external.message.mapper;

import com.logistic.common.internal.message.CancelOrderEvent;
import com.logistic.common.internal.message.DeleteCompanyEvent;
import com.logistic.product.application.port.in.command.DeleteProductCommand;
import com.logistic.product.application.port.in.command.UpdateStockCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductEventMapper {
  UpdateStockCommand toUpdateStockCommand(CancelOrderEvent cancelOrderEvent);

  DeleteProductCommand toDeleteProductCommand(DeleteCompanyEvent deleteCompanyEvent);
}
