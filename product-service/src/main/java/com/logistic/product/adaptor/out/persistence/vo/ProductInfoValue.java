package com.logistic.product.adaptor.out.persistence.vo;

import jakarta.persistence.Column;
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
public class ProductInfoValue {
  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "company_id", nullable = false)
  private Long companyId;
}
