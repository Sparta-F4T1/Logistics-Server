package com.logistic.company.adapter.out.internal.mapper;

import com.logistic.common.internal.response.GpsClientResponse;
import com.logistic.common.internal.response.HubClientResponse;
import com.logistic.common.internal.response.UserClientResponse;
import com.logistic.company.domain.model.vo.Gps;
import com.logistic.company.domain.model.vo.Hub;
import com.logistic.company.domain.model.vo.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyClientMapper {

  Gps toGps(GpsClientResponse response);

  Hub toHub(HubClientResponse response);

  User toUser(UserClientResponse response);
}
