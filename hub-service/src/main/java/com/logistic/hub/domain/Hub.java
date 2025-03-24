package com.logistic.hub.domain;

import com.logistic.hub.application.port.in.command.HubCreateCommand;
import com.logistic.hub.application.port.in.command.HubUpdateCommand;
import com.logistic.hub.application.port.in.command.UserInfoCommand;
import com.logistic.hub.domain.command.AddressCommand;
import com.logistic.hub.domain.vo.Address;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hub {

  private Long id;
  private HubType hubType;
  private String hubName;
  private Address address;
  private Boolean isDeleted;
  private List<String> managerIds;


  public static Hub createHub(HubCreateCommand hubCommand, AddressCommand addressCommand) {
    Address address = new Address(addressCommand.road(), addressCommand.jibun(), addressCommand.latitude(),
        addressCommand.longitude());
    String userId = hubCommand.passport().getUserInfo().getUserId();
    return Hub.builder().
        hubName(hubCommand.hubName())
        .hubType(HubType.valueOf(hubCommand.hubType()))
        .address(address)
        .managerIds(new ArrayList<>(List.of(userId)))
        .isDeleted(false)
        .build();
  }

  public void update(HubUpdateCommand hubCommand, AddressCommand addressCommand) {
    Address address = new Address(addressCommand.road(), addressCommand.jibun(), addressCommand.latitude(),
        addressCommand.longitude());
    String userId = hubCommand.passport().getUserInfo().getUserId();
    if (!managerIds.contains(userId)) {
      managerIds.add(userId);
    }
    this.hubType = HubType.valueOf(hubCommand.hubType());
    this.hubName = hubCommand.hubName();
    this.address = address;
  }

  public void assignManager(List<UserInfoCommand> userList) {

    Set<String> managerSet = new HashSet<>(managerIds);
    for (UserInfoCommand userInfoCommand : userList) {
      managerSet.add(userInfoCommand.userId());
    }
    managerIds = new ArrayList<>(managerSet);
  }

  public void deleteManager(List<String> userIds) {

    for (String userId : userIds) {
      managerIds.remove(userId);
    }
  }
}
