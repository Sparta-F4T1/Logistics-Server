package com.logistic.hub.adaptor.out;

import com.logistic.hub.application.port.out.NaverClientPort;
import com.logistic.hub.domain.command.AddressCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NaverClientAdaptor implements NaverClientPort {

  @Override
  public AddressCommand getAddressCommand(String roadAddress, String jibunAddress) {
    return new AddressCommand(roadAddress, jibunAddress, 300.0, 37.0); //임시
  }
}
