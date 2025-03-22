package com.logistic.company.adapter.out.persistence.repository;

import com.logistic.company.adapter.out.persistence.model.CompanyViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyViewJpaRepository extends JpaRepository<CompanyViewEntity, Long> {
}
