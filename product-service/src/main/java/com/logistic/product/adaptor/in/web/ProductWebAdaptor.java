package com.logistic.product.adaptor.in.web;

import com.logistic.common.annotation.WebAdapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.product.adaptor.in.web.mapper.ProductWebMapper;
import com.logistic.product.adaptor.in.web.request.ProductCreateRequest;
import com.logistic.product.adaptor.in.web.request.ProductInfoUpdateRequest;
import com.logistic.product.adaptor.in.web.response.ProductCreateResponse;
import com.logistic.product.adaptor.in.web.response.ProductResponse;
import com.logistic.product.application.port.in.ProductUseCase;
import com.logistic.product.domain.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@WebAdapter
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductWebAdaptor {
  private final ProductUseCase productUseCase;
  private final ProductWebMapper productWebMapper;

  @PostMapping
  public ResponseEntity<ApiResponse<ProductCreateResponse>> createProduct(
      @Valid @RequestBody final ProductCreateRequest request) {
    final Product product = productUseCase.createProduct(productWebMapper.toCreateCommand(request));
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(productWebMapper.toCreateResponse(product)));
  }

  @PatchMapping("/{productId}")
  public ResponseEntity<ApiResponse<ProductResponse>> updateProductInfo(
      @PathVariable final Long productId,
      @RequestBody final ProductInfoUpdateRequest request) {
    final Product product = productUseCase.updateProductInfo(productWebMapper.toUpdateInfoCommand(productId, request));
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(productWebMapper.toProductResponse(product)));
  }

  @PatchMapping("/{productId}/stock")
  public ResponseEntity<ApiResponse<ProductResponse>> updateStock(
      @PathVariable final Long productId,
      @RequestParam final Integer quantity) {
    final Product product = productUseCase.updateStock(productWebMapper.toUpdateStockCommand(productId, quantity));
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(productWebMapper.toProductResponse(product)));
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<ApiResponse<Void>> deleteProduct(
      @PathVariable final Long productId) {
    productUseCase.deleteProduct(productWebMapper.toDeleteCommand(productId));
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(null));
  }

}