package com.logistic.product.adaptor.in.web.mapper;

import com.logistic.product.adaptor.in.web.request.ProductCreateRequest;
import com.logistic.product.adaptor.in.web.response.ProductCreateResponse;
import com.logistic.product.application.port.in.command.ProductCreateCommand;
import com.logistic.product.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductWebMapper {

  ProductCreateCommand toCreateCommand(ProductCreateRequest productCreateRequest);

  @Mapping(source = "id", target = "productId")
  ProductCreateResponse toResponse(Product product);
}
