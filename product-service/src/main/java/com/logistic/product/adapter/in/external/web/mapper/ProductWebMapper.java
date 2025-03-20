package com.logistic.product.adapter.in.external.web.mapper;

import com.logistic.product.adapter.in.external.web.request.CreateProductRequest;
import com.logistic.product.adapter.in.external.web.request.SearchProductRequest;
import com.logistic.product.adapter.in.external.web.request.UpdateProductRequest;
import com.logistic.product.adapter.in.external.web.response.FindProductResponse;
import com.logistic.product.application.port.in.command.CreateProductCommand;
import com.logistic.product.application.port.in.command.DeleteProductCommand;
import com.logistic.product.application.port.in.command.UpdateProductCommand;
import com.logistic.product.application.port.in.query.FindProductQuery;
import com.logistic.product.application.port.in.query.SearchProductQuery;
import com.logistic.product.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Pageable;

@Mapper(componentModel = "spring")
public interface ProductWebMapper {

  CreateProductCommand toCreateCommand(CreateProductRequest createProductRequest);

  UpdateProductCommand toUpdateInfoCommand(Long productId, UpdateProductRequest request);

  @Mapping(source = "id", target = "productId")
  @Mapping(source = "stock.quantity", target = "quantity")
  @Mapping(source = "info.companyId", target = "companyId")
  @Mapping(source = "info.name", target = "name")
  FindProductResponse toProductResponse(Product product);

  DeleteProductCommand toDeleteCommand(Long productId);

  FindProductQuery toFindQuery(Long productId);

  SearchProductQuery toSearchQuery(SearchProductRequest request, Pageable pageable);
}
