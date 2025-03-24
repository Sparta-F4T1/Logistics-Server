package com.logistic.product.adapter.in.internal.mapper;

import com.logistic.common.internal.request.ProductClientRequest;
import com.logistic.common.internal.response.ProductClientResponse;
import com.logistic.common.passport.model.Passport;
import com.logistic.product.application.port.in.command.UpdateStockCommand;
import com.logistic.product.application.port.in.query.FindProductQuery;
import com.logistic.product.application.port.in.query.ListProductQuery;
import com.logistic.product.domain.Product;
import java.util.Map;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductInternalMapper {

  UpdateStockCommand toDecreaseCommand(Map<Long, Integer> stockMap, Passport passport);

  UpdateStockCommand toUpdateCommand(ProductClientRequest request, Passport passport);

  @Mapping(source = "id", target = "productId")
  @Mapping(source = "stock.quantity", target = "quantity")
  ProductClientResponse toResponse(Product product);

  FindProductQuery toFindQuery(Long productId, Passport passport);

  ListProductQuery toListQuery(ProductClientRequest request, Passport passport);
}
