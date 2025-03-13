package com.logistic.company.adaptor.out.persistence.repository;

import com.logistic.company.adaptor.out.persistence.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyJpaRepository extends JpaRepository<CompanyEntity, Long> {

}