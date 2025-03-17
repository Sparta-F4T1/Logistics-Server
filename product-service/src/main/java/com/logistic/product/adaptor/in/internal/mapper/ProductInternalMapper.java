package com.logistic.product.adaptor.in.internal.mapper;

import com.logistic.product.adaptor.in.internal.response.ProductDto;
import com.logistic.product.application.port.in.command.StockAddCommand;
import com.logistic.product.application.port.in.command.StockDecreaseCommand;
import com.logistic.product.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductInternalMapper {
  StockAddCommand toStockAddCommand(Long productId, Integer quantity);

  StockDecreaseCommand toStockDecreaseCommand(Long productId, Integer quantity);

  @Mapping(source = "id", target = "productId")
  @Mapping(source = "stock.quantity", target = "quantity")
  ProductDto toProductDto(Product product);
}
