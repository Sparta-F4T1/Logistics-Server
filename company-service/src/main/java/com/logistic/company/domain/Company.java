package com.logistic.company.domain;

import com.logistic.company.domain.vo.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Company {
  private Long id;
  private String name;
  private CompanyType type;
  private Address address;
  private String manager;
  private Long hubId;
  private Boolean isDeleted;

  public static Company create(final String name, final CompanyType type, final Address address, final String manager,
                               final Long hubId) {
    return Company.builder()
        .name(name)
        .type(type)
        .address(address)
        .manager(manager)
        .hubId(hubId)
        .isDeleted(false)
        .build();
  }
}
