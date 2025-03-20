package com.logistic.product.adapter.out.persistence.mapper;

import com.logistic.product.adapter.out.persistence.model.ProductEntity;
import com.logistic.product.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductPersistenceMapper {

  @Mapping(source = "info", target = "info")
  @Mapping(source = "stock", target = "stock")
  Product toDomain(ProductEntity productEntity);

  @Mapping(source = "info", target = "info")
  @Mapping(source = "stock", target = "stock")
  ProductEntity toEntity(Product product);
}
