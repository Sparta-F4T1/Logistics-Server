package com.logistic.delivery.adapter.in.web;

import com.logistic.common.annotation.Adapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.delivery.adapter.in.web.mapper.DeliveryWebMapper;
import com.logistic.delivery.adapter.in.web.request.DeliveryCreateRequest;
import com.logistic.delivery.adapter.in.web.response.DeliveryResponse;
import com.logistic.delivery.application.port.in.DeliveryUseCase;
import com.logistic.delivery.domain.Delivery;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Adapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deliveries")
public class DeliveryWebAdapter {
  private final DeliveryUseCase deliveryUseCase;
  private final DeliveryWebMapper deliveryWebMapper;

  @GetMapping
  public ResponseEntity<ApiResponse<DeliveryResponse>> createDelivery(
      @Valid @RequestBody DeliveryCreateRequest request
  ) {
    Delivery delivery = deliveryUseCase.createDelivery(deliveryWebMapper.toCreateCommand(request));
    DeliveryResponse response = deliveryWebMapper.toResponse(delivery);
    return ResponseEntity.ok().body(ApiResponse.success(response));
  }

}
