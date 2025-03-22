package com.logistic.company.domain.model;

import com.logistic.company.domain.command.CompanyForCreate;
import com.logistic.company.domain.command.CompanyForUpdate;
import com.logistic.company.domain.model.vo.Gps;
import com.logistic.company.domain.model.vo.User;
import java.util.List;
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
  private Gps gps;
  private Long hubId;
  private List<String> managerIds;
  private Boolean isDeleted;

  public static Company create(final CompanyForCreate forCreate) {
    return Company.builder()
        .name(forCreate.name())
        .type(forCreate.type())
        .gps(forCreate.gps())
        .hubId(forCreate.hub().hubId())
        .managerIds(forCreate.users().stream().map(User::userId).toList())
        .isDeleted(false)
        .build();
  }

  public void updateCompany(final CompanyForUpdate forUpdate) {
    this.name = forUpdate.name();
    this.type = forUpdate.type();
    this.gps = forUpdate.gps();
    this.hubId = forUpdate.hub().hubId();
    this.managerIds = forUpdate.users().stream().map(User::userId).toList();
  }

  public void delete() {
    this.isDeleted = true;
  }
}
