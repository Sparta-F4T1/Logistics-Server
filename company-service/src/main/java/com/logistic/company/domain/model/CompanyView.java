package com.logistic.company.domain.model;

import com.logistic.company.domain.command.CompanyViewForCreate;
import com.logistic.company.domain.model.vo.Gps;
import com.logistic.company.domain.model.vo.Hub;
import com.logistic.company.domain.model.vo.User;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanyView {
  private Long companyId;
  private String name;
  private CompanyType type;
  private Gps gps;
  private Hub hub;
  private List<User> managers;
  private Boolean isDeleted;

  public static CompanyView create(final CompanyViewForCreate forCreate) {
    return CompanyView.builder()
        .companyId(forCreate.companyId())
        .name(forCreate.name())
        .type(forCreate.type())
        .gps(forCreate.gps())
        .hub(forCreate.hub())
        .managers(forCreate.users())
        .isDeleted(false)
        .build();
  }
}
