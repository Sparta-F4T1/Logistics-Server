package com.logistic.company.adapter.out.persistence.model;

import com.logistic.company.adapter.out.persistence.model.vo.GpsVo;
import com.logistic.company.domain.model.CompanyType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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

@Entity
@Getter
@Builder
@Table(name = "p_company")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyEntity extends TimeStamped {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "company_id")
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private CompanyType type;

  @Embedded
  private GpsVo gps;

  @Column(name = "hub_id", nullable = false)
  private Long hubId;

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(
      name = "manager_ids",
      joinColumns = @JoinColumn(name = "company_id")
  )
  private List<String> managerIds;

  @Column(name = "is_deleted")
  private Boolean isDeleted;
}
