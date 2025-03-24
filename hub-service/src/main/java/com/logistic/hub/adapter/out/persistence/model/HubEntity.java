package com.logistic.hub.adapter.out.persistence.model;

import com.logistic.hub.adapter.out.persistence.AddressValue;
import com.logistic.hub.domain.HubType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;


@Entity
@Table(name = "p_hub")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "hub_id")
  @Comment("허브 기본키")
  private Long id;

  @Column(name = "hub_type", nullable = false)
  @Enumerated(EnumType.STRING)
  @Comment("허브 유형")
  private HubType hubType;

  @Column(name = "hub_name", nullable = false)
  @Comment("허브명")
  private String hubName;

  @Embedded
  private AddressValue address;

  @ElementCollection
  @CollectionTable(name = "manager_ids", joinColumns = @JoinColumn(name = "hub_id"))
  private List<String> managerIds;
}