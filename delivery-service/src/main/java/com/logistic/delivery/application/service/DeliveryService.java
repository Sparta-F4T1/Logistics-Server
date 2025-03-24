package com.logistic.delivery.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.delivery.application.port.in.DeliveryUseCase;
import com.logistic.delivery.application.port.in.command.DeliveryCreateCommand;
import com.logistic.delivery.application.port.in.command.DeliveryDeleteCommand;
import com.logistic.delivery.application.port.in.command.DeliveryUpdateCommand;
import com.logistic.delivery.application.port.in.command.HubDeliveryHistoryUpdateCommand;
import com.logistic.delivery.application.port.out.DeliveryInternalPort;
import com.logistic.delivery.application.port.out.DeliveryPersistencePort;
import com.logistic.delivery.domain.Delivery;
import com.logistic.delivery.domain.DeliveryStatus;
import com.logistic.delivery.domain.HubDeliveryHistory;
import com.logistic.delivery.domain.vo.Distance;
import com.logistic.delivery.domain.vo.Sequence;
import com.logistic.delivery.domain.vo.Time;
import com.logistic.delivery.domain.vo.dto.HubDriverInfo;
import com.logistic.delivery.domain.vo.dto.HubRouteInfo;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class DeliveryService implements DeliveryUseCase {

  private final DeliveryPersistencePort deliveryPersistencePort;
  private final DeliveryInternalPort internalClientPort;

  @Override
  public Delivery createDelivery(DeliveryCreateCommand command) {
    // 1. 허브 경로 가져오기
    List<HubRouteInfo> hubRoutes = internalClientPort.getHubRoutes(command.departHubId(),command.arrivalHubId());

    // 2. 허브 배송 담당자 가져오기
    List<HubDriverInfo> hubDrivers = internalClientPort.getHubDrivers(hubRoutes);

    // todo : client에서 던지는 예외처리
    // todo : hub route 조회 및 List<HubDeliveryHistory> 로 변환

//    List<HubDeliveryHistory> histories = createTestHubDeliveryHistory(); //임시
    List<HubDeliveryHistory> histories = createHubDeliveryHistory(hubRoutes,hubDrivers);

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

  @Override
  public Delivery updateDelivery(Long deliveryId, DeliveryUpdateCommand command) {
    // todo: Delivery NOT FOUND 예외 / domain.exception
    Delivery delivery = deliveryPersistencePort.findById(deliveryId).orElseThrow(null);
    command.status().ifPresent(delivery::updateStatus);
    command.driverId().ifPresent(delivery::updateDriverId);

    return deliveryPersistencePort.save(delivery);
  }

  @Override
  public HubDeliveryHistory updateHubDeliveryHistory(Long deliveryId, HubDeliveryHistoryUpdateCommand command) {
    // todo: Delivery NOT FOUND 예외 / domain.exception
    Delivery delivery = deliveryPersistencePort.findById(deliveryId).orElseThrow(null);
    // todo: HubDeliveryHistory NOT FOUND 예외 / domain.exception
    HubDeliveryHistory history = delivery.getHubDeliveryHistories().stream()
        .filter(h ->
            h.getDepartHubId().equals(command.departHubId()) &&
            h.getArrivalHubId().equals(command.arrivalHubId()))
        .findFirst()
        .orElseThrow(null);
    history.updateStatus(command.status());

    deliveryPersistencePort.save(delivery);
    return history;
  }

  @Override
  public void deleteDelivery(DeliveryDeleteCommand command) {
    // todo: 예외처리
    Delivery delivery = deliveryPersistencePort.findById(command.deliveryId()).orElseThrow(null);
    delivery.delete();
    deliveryPersistencePort.save(delivery);
  }

  private List<HubDeliveryHistory> createHubDeliveryHistory(
      List<HubRouteInfo> hubRoutes,
      List<HubDriverInfo> hubDrivers
  ) {
    int endIndex = hubRoutes.size();
    AtomicInteger index = new AtomicInteger(1);

    return hubRoutes.stream()
        .flatMap(route -> hubDrivers.stream()
            .filter(driver ->
                route.departHubId().equals(driver.departHubId()) &&
                route.arrivalHubId().equals(driver.arrivalHubId()))
            .map(driver -> {
              Time time = new Time(route.duration());
              Distance distance = new Distance(route.distance().doubleValue());
              Sequence sequence = new Sequence(index.getAndIncrement(), endIndex);

              return HubDeliveryHistory.builder()
                  .sequence(sequence)
                  .departHubId(route.departHubId())
                  .arrivalHubId(route.arrivalHubId())
                  .time(time)
                  .distance(distance)
                  .status(DeliveryStatus.HUB_WAITING)
                  .driverId(driver.driverId())
                  .build();
            }))
        .toList();
  }

  private List<HubDeliveryHistory> createTestHubDeliveryHistory() {
    HubDeliveryHistory history1 = HubDeliveryHistory.builder()
        .sequence(new Sequence(1, 3))
        .departHubId(6L)
        .arrivalHubId(7L)
        .time(new Time(720))
        .distance(new Distance(3.12))
        .status(DeliveryStatus.HUB_TRANSIT)
        .driverId("driver1")
        .build();

    HubDeliveryHistory history2 = HubDeliveryHistory.builder()
        .sequence(new Sequence(2, 3))
        .departHubId(7L)
        .arrivalHubId(8L)
        .time(new Time(720))
        .distance(new Distance(3.12))
        .status(DeliveryStatus.HUB_TRANSIT)
        .driverId("driver2")
        .build();

    HubDeliveryHistory history3 = HubDeliveryHistory.builder()
        .sequence(new Sequence(3, 3))
        .departHubId(8L)
        .arrivalHubId(9L)
        .time(new Time(720))
        .distance(new Distance(3.12))
        .status(DeliveryStatus.HUB_TRANSIT)
        .driverId("driver3")
        .build();

    return List.of(history1, history2, history3);
  }

}
