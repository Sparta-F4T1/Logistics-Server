package com.logistic.order.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company{
  private Long companyId;
  private String companyName;
  private Long hubId;

  public static Company create(Long companyId, String companyName, Long hubId){
    return Company.builder()
        .companyId(companyId)
        .companyName(companyName)
        .hubId(hubId)
        .build();
  }
}
