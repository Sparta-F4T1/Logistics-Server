package com.logistic.hub.adapter.out.persistence.repository;

import com.logistic.hub.adapter.out.persistence.entity.HubEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubJpaRepository extends JpaRepository<HubEntity, Long> {
}
