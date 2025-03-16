package com.logistic.hub.application.port.out;

import com.logistic.hub.domain.command.AddressCommand;

public interface NaverClientPort {
  AddressCommand getAddressCommand(String roadAddress, String jibunAddress);
}
