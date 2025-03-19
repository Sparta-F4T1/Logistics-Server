package com.logistic.driver.adapter.out.persistence.model.entity;

import com.logistic.driver.domain.DriverType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "p_driver")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DriverEntity extends TimeStamped {

  @Id
  @Column(name = "driver_id", nullable = false)
  private String id;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private DriverType type;

  @Column(name = "depart_hub_id", nullable = false)
  private Long departHubId;

  @Column(name = "arrival_hub_id", nullable = false)
  private Long arrivalHubId;

  @Column(name = "is_deleted")
  private Boolean isDeleted;
}
