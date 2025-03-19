package com.logistic.delivery.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.delivery.application.port.in.DeliveryUseCase;
import com.logistic.delivery.application.port.in.command.DeliveryCreateCommand;
import com.logistic.delivery.application.port.out.DeliveryPersistencePort;
import com.logistic.delivery.application.port.out.DeliveryInternalPort;
import com.logistic.delivery.domain.Delivery;
import com.logistic.delivery.domain.DeliveryStatus;
import com.logistic.delivery.domain.HubDeliveryHistory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class DeliveryService implements DeliveryUseCase {

  private final DeliveryPersistencePort deliveryPersistencePort;
  private final DeliveryInternalPort internalClientPort;

  @Override
  public Delivery createDelivery(DeliveryCreateCommand command) {
    // 1. 허브 경로 가져오기
//    List<HubRouteInfo> hubRouteInfos = internalClientPort.getHubRoute(command.departHubId(),command.arrivalHubId());

    // 2. 허브 배송 담당자 가져오기?
//    List<HubDriverInfo> hubDrivers = internalClientPort.getHubDriver(List<hubRouteDto> hubRoutes);

    // todo : client에서 던지는 예외처리
    // todo : hub route 조회 및 List<HubDeliveryHistory> 로 변환

    List<HubDeliveryHistory> histories = new ArrayList<>(); //임시

    return deliveryPersistencePort.save(
        Delivery.create(
            command.orderId(),
            DeliveryStatus.HUB_WAITING,
            command.departCompanyId(),
            command.arrivalHubId(),
            command.departHubId(),
            command.arrivalHubId(),
            histories
        )
    );
  }

}
