package com.logistic.company.adapter.out.persistence.model.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubVo {
  private Long hubId;
  private String hubType;
  private String hubName;
  private String hubRoad;
  private String hubJibun;
  private Double hubLatitude;
  private Double hubLongitude;
}
