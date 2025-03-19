package com.logistic.hub.adaptor.out.persistence.repository;

import com.logistic.hub.adaptor.out.persistence.entity.HubEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubJpaRepository extends JpaRepository<HubEntity, Long> {
}
