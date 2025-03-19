package com.logistic.delivery.application.service;

import com.logistic.delivery.application.port.in.command.DeliveryCreateCommand;
import com.logistic.delivery.domain.Delivery;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class DeliveryServiceTest {

  @Autowired
  private DeliveryService deliveryService;

  @Test
  @DisplayName("배송 생성 API 테스트")
  void create_success() {
    // Given
    DeliveryCreateCommand command = DeliveryCreateCommand.builder()
        .orderId(1L)
        .departHubId(2L)
        .arrivalHubId(3L)
        .arrivalCompanyId(4L)
        .departCompanyId(5L)
        .build();

    // When
    Delivery delivery = deliveryService.createDelivery(command);

    // Then
    Assertions.assertThat(delivery).isNotNull();
    Assertions.assertThat(delivery.getId()).isEqualTo(1L);
    Assertions.assertThat(delivery.getOrderId()).isEqualTo(command.orderId());
    Assertions.assertThat(delivery.getDepartHubId()).isEqualTo(command.departCompanyId());
    Assertions.assertThat(delivery.getArrivalHubId()).isEqualTo(command.arrivalHubId());
    Assertions.assertThat(delivery.getDepartCompanyId()).isEqualTo(command.departCompanyId());
    Assertions.assertThat(delivery.getArrivalCompanyId()).isEqualTo(command.departCompanyId());
  }
}