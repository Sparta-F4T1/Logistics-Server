package com.logistic.company.application.port.out;

import com.logistic.company.domain.vo.Address;

public interface HubServicePort {
  Address getAddress(String road);
}
