package com.logistic.company.adapter.out.internal.mapper;

import com.logistic.common.internal.request.GpsClientRequest;
import com.logistic.common.internal.request.HubClientRequest;
import com.logistic.common.internal.request.UserClientRequest;
import com.logistic.common.internal.response.GpsClientResponse;
import com.logistic.common.internal.response.HubClientResponse;
import com.logistic.common.internal.response.UserClientResponse;
import com.logistic.common.passport.model.Passport;
import com.logistic.company.domain.model.vo.Gps;
import com.logistic.company.domain.model.vo.Hub;
import com.logistic.company.domain.model.vo.User;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyClientMapper {
  GpsClientRequest toGpsRequest(String road);

  Gps toGps(GpsClientResponse response);

  HubClientRequest toHubRequest(List<Long> hubIds, Passport passport);

  Hub toHub(HubClientResponse response);

  UserClientRequest toUserRequest(List<String> userIds, Passport passport);

  User toUser(UserClientResponse response);
}
