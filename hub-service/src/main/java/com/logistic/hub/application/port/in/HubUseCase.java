package com.logistic.hub.application.port.in;

import com.logistic.hub.application.port.in.command.HubCreateCommand;
import com.logistic.hub.application.port.in.command.HubDeleteCommand;
import com.logistic.hub.application.port.in.command.HubUpdateCommand;
import com.logistic.hub.application.port.in.command.RouteDeleteByHubIdCommand;
import com.logistic.hub.domain.Hub;

public interface HubUseCase {

  Hub createHub(HubCreateCommand command);

  void updateHub(HubUpdateCommand command, RouteDeleteByHubIdCommand routeCommand);

  void deleteHub(HubDeleteCommand command, RouteDeleteByHubIdCommand routeCommand);

}
