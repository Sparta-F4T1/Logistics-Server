package com.logistic.hub.adapter.out.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "p_route")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RouteEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "route_id")
  @Comment("경로 기본키")
  private Long id;

  @Column(name = "depart_hub_id", nullable = false)
  @Comment("출발 허브 아이디")
  private Long departHubId;

  @Column(name = "arrival_hub_id", nullable = false)
  @Comment("도착 허브 아이디")
  private Long arrivalHubId;

  @Column(name = "distance", nullable = false)
  @Comment("거리")
  private Integer distance;

  @Column(name = "duration", nullable = false)
  @Comment("시간")
  private Integer duration;
}