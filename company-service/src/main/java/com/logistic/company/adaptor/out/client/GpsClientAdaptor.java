package com.logistic.company.adaptor.out.client;

import com.logistic.company.application.port.out.GpsClientPort;
import com.logistic.company.domain.vo.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GpsClientAdaptor implements GpsClientPort {
  private final GpsFeignClient gpsFeignClient;

  @Override
  public Address getAddress(String road) {
    //todo String 주소 -> 도로명,지번,위도,경도 변환 로직 구현
    return new Address(road, "지번주소", 35.0, 30.0);
  }
}
