package com.logistic.product.adapter.out.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public abstract class TimeStamped {

  @CreatedDate
  @Column(nullable = false, updatable = false)
  @Temporal(value = TemporalType.TIMESTAMP)
  protected LocalDateTime createdAt;

  @LastModifiedDate
  @Temporal(value = TemporalType.TIMESTAMP)
  protected LocalDateTime updatedAt;

}
