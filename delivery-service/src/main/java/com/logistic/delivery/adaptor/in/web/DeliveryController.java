package com.logistic.delivery.adaptor.in.web;

import com.logistic.common.annotation.WebAdapter;
import com.logistic.common.response.ApiResponse;
import com.logistic.delivery.adaptor.in.web.mapper.DeliveryWebMapper;
import com.logistic.delivery.adaptor.in.web.request.DeliveryCreateRequest;
import com.logistic.delivery.adaptor.in.web.response.DeliveryResponse;
import com.logistic.delivery.application.port.in.DeliveryUseCase;
import com.logistic.delivery.domain.Delivery;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {
  private final DeliveryUseCase deliveryUseCase;
  private final DeliveryWebMapper deliveryWebMapper;

  @GetMapping
  public ResponseEntity<ApiResponse<DeliveryResponse>> createDelivery(@Valid @RequestBody DeliveryCreateRequest request){
    Delivery delivery = deliveryUseCase.createDelivery(deliveryWebMapper.toCreateCommand(request));
    DeliveryResponse response = deliveryWebMapper.toResponse(delivery);
    return ResponseEntity.ok().body(ApiResponse.success(response));
  }
}
