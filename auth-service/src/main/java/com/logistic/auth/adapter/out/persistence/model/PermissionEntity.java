package com.logistic.auth.adapter.out.persistence.model;

import com.logistic.auth.domain.vo.ActionType;
import com.logistic.auth.domain.vo.ResourceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Builder
@Table(name = "p_permission")
@Comment("권한 정보 테이블")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PermissionEntity extends BaseEntity {
  @Id
  @Column(name = "permission_id")
  @Comment("권한 식별자")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "resource_type", nullable = false)
  @Comment("권한이 적용되는 리소스 타입 (USER, COMPANY, HUB 등)")
  private ResourceType resourceType;

  @Enumerated(EnumType.STRING)
  @Column(name = "action_type", nullable = false)
  @Comment("권한에 대한 액션 타입 (CREATE, READ, UPDATE, DELETE 등)")
  private ActionType actionType;

  @Column(name = "description", nullable = false)
  @Comment("권한에 대한 설명")
  private String description;

  @OneToMany(mappedBy = "permission")
  private Set<RolePermissionEntity> rolePermissions = new HashSet<>();
}