package com.logistic.auth.adapter.out.persistence.model;


import com.logistic.auth.domain.vo.RoleType;
import jakarta.persistence.CascadeType;
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
@Table(name = "p_role")
@Comment("역할 정보 테이블")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleEntity extends BaseEntity {

  @Id
  @Column(name = "role_id")
  @Comment("역할 식별자")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "name", nullable = false, unique = true)
  @Comment("역할 이름 (MASTER_ADMIN, HUB_ADMIN, DELIVERY_PERSONNEL 등)")
  private RoleType name;

  @Column(name = "description", nullable = false)
  @Comment("역할에 대한 설명")
  private String description;

  @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<RolePermissionEntity> rolePermissions = new HashSet<>();
}