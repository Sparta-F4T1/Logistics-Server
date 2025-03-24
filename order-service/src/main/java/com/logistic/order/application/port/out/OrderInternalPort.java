package com.logistic.order.application.port.out;

import com.logistic.order.application.service.dto.CompanyDto;
import com.logistic.order.application.service.dto.HubDto;
import com.logistic.order.application.service.dto.UserDto;
import com.logistic.order.domain.vo.OrderProduct;
import java.util.List;

public interface OrderInternalPort {
  void updateStock(List<OrderProduct> orderProducts);

  CompanyDto findCompany(Long companyId);

  HubDto findHub(Long hubId);

  UserDto findUser(String userId);

}
