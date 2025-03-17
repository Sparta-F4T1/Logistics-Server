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

  public void updateCompany(final String name, final CompanyType type, final Address address, final String manager,
                            final Long hubId) {
    this.name = name;
    this.type = type;
    this.address = address;
    this.manager = manager;
    this.hubId = hubId;
  }

  public void softDelete() {
    // todo 삭제 예외 구현
    this.isDeleted = true;
  }
}
