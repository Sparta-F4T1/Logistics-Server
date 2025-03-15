package com.logistic.product.adaptor.out.persistence.mapper;

import com.logistic.product.adaptor.out.persistence.ProductEntity;
import com.logistic.product.domain.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductPersistenceMapper {
  Product toDomain(ProductEntity productEntity);

  ProductEntity toEntity(Product product);
}
