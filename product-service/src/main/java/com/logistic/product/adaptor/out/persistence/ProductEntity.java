package com.logistic.product.adaptor.out.persistence;

import com.logistic.product.adaptor.out.persistence.vo.ProductInfoValue;
import com.logistic.product.adaptor.out.persistence.vo.StockValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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

@Entity
@Getter
@Builder
@Table(name = "p_product")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEntity extends TimeStamped {

  @Id
  @Column(name = "product_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private ProductInfoValue info;

  @Embedded
  private StockValue stock;

  @Column(name = "is_deleted", nullable = false)
  private Boolean isDeleted;
}
