package com.logistic.product.adapter.in.internal.mapper;

import com.logistic.common.internal.response.ProductClientResponse;
import com.logistic.common.passport.model.Passport;
import com.logistic.product.application.port.in.command.AddStockCommand;
import com.logistic.product.application.port.in.command.DecreaseStockCommand;
import com.logistic.product.application.port.in.query.FindProductQuery;
import com.logistic.product.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductInternalMapper {
  AddStockCommand toAddCommand(Long productId, Integer quantity);

  DecreaseStockCommand toDecreaseCommand(Long productId, Integer quantity);

  @Mapping(source = "id", target = "productId")
  @Mapping(source = "stock.quantity", target = "quantity")
  ProductClientResponse toResponse(Product product);

  FindProductQuery toFindQuery(Long productId, Passport passport);
}
