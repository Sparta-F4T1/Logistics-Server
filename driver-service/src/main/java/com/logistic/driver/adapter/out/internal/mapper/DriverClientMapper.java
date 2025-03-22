package com.logistic.driver.adapter.out.internal.mapper;

import com.logistic.common.internal.response.HubClientResponse;
import com.logistic.common.internal.response.UserClientResponse;
import com.logistic.driver.domain.model.vo.Hub;
import com.logistic.driver.domain.model.vo.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverClientMapper {
  Hub toHub(HubClientResponse hubClientResponse);

  User toUser(UserClientResponse userClientResponse);
}
