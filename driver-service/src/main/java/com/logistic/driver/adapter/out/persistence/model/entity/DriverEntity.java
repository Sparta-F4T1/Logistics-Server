package com.logistic.driver.adapter.out.persistence.model.entity;

import com.logistic.driver.domain.model.vo.DriverStatus;
import com.logistic.driver.domain.model.vo.DriverType;
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

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private DriverStatus status;

  @Column(name = "hub_id", nullable = false)
  private Long hubId;

  @Column(name = "is_deleted")
  private Boolean isDeleted;
}
