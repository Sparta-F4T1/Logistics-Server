package com.logistic.company.adapter.out.persistence.repository;

import com.logistic.company.adapter.out.persistence.model.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyJpaRepository extends JpaRepository<CompanyEntity, Long> {

}