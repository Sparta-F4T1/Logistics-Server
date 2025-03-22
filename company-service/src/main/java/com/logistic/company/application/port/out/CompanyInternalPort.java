package com.logistic.company.application.port.out;

import com.logistic.company.domain.model.vo.Gps;
import com.logistic.company.domain.model.vo.Hub;
import com.logistic.company.domain.model.vo.User;
import java.util.List;

public interface CompanyInternalPort {
  Gps findGps(String road);

  Hub findHub(Long hubId);

  List<User> findUserList(List<String> userIds);
}
