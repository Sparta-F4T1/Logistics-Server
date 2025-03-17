package com.logistic.hub.adaptor.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HubJpaRepository extends JpaRepository<HubEntity, Long> {
}
