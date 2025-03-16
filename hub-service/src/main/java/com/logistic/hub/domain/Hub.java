package com.logistic.hub.domain;

import com.logistic.hub.application.port.in.command.HubCreateCommand;
import com.logistic.hub.application.port.in.command.HubUpdateCommand;
import com.logistic.hub.domain.command.AddressCommand;
import com.logistic.hub.domain.vo.Address;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Hub {

  private Long id;
  private HubType hubType;
  private String hubName;
  private Address address;
  private Boolean isDeleted;


  public static Hub createHub(HubCreateCommand hubCommand, AddressCommand addressCommand) {
    Address address = new Address(addressCommand.road(), addressCommand.jibun(), addressCommand.latitude(),
        addressCommand.longitude());
    return Hub.builder().
        hubName(hubCommand.hubName())
        .hubType(HubType.valueOf(hubCommand.hubType()))
        .address(address)
        .isDeleted(false)
        .build();
  }

  public void update(HubUpdateCommand hubCommand, AddressCommand addressCommand) {
    Address address = new Address(addressCommand.road(), addressCommand.jibun(), addressCommand.latitude(),
        addressCommand.longitude());
    this.hubType = HubType.valueOf(hubCommand.hubType());
    this.hubName = hubCommand.hubName();
    this.address = address;
  }
}
