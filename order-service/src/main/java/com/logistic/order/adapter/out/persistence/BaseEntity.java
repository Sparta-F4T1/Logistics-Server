package com.logistic.order.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

  @CreatedDate
  @Column(nullable = true, updatable = false)
  @Temporal(value = TemporalType.TIMESTAMP)
  protected LocalDateTime createdAt;

  @CreatedBy
  @Column(nullable = true, updatable = false)
  protected String createdBy;

  @LastModifiedDate
  @Temporal(value = TemporalType.TIMESTAMP)
  protected LocalDateTime updatedAt;

  @LastModifiedBy
  protected String updatedBy;

  @Builder.Default
  protected Boolean isDeleted = false;

  @Temporal(value = TemporalType.TIMESTAMP)
  protected LocalDateTime deletedAt;
  protected String deletedBy;

  public void delete(boolean flag, String userId) {
    this.deletedAt = LocalDateTime.now();
    this.isDeleted = flag;
    this.deletedBy = userId;
  }
}
