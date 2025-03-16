package com.logistic.product.adaptor.in.web.mapper;

import com.logistic.product.adaptor.in.web.request.ProductCreateRequest;
import com.logistic.product.adaptor.in.web.request.ProductInfoUpdateRequest;
import com.logistic.product.adaptor.in.web.response.ProductCreateResponse;
import com.logistic.product.adaptor.in.web.response.ProductResponse;
import com.logistic.product.application.port.in.command.ProductCreateCommand;
import com.logistic.product.application.port.in.command.ProductDeleteCommand;
import com.logistic.product.application.port.in.command.ProductInfoUpdateCommand;
import com.logistic.product.application.port.in.command.StockUpdateCommand;
import com.logistic.product.application.port.in.query.ProductFindQuery;
import com.logistic.product.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductWebMapper {

  ProductCreateCommand toCreateCommand(ProductCreateRequest productCreateRequest);

  @Mapping(source = "id", target = "productId")
  ProductCreateResponse toCreateResponse(Product product);

  ProductInfoUpdateCommand toUpdateInfoCommand(Long productId, ProductInfoUpdateRequest request);

  StockUpdateCommand toUpdateStockCommand(Long productId, Integer quantity);

  @Mapping(source = "id", target = "productId")
  ProductResponse toProductResponse(Product product);

  ProductDeleteCommand toDeleteCommand(Long productId);

  ProductFindQuery toFindQuery(Long productId);
}
