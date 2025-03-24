package com.logistic.hub.adapter.out.persistence.repository;

import com.logistic.hub.adapter.out.persistence.model.HubEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubJpaRepository extends JpaRepository<HubEntity, Long> {
  List<HubEntity> findAllByIdInAndIsDeletedFalse(List<Long> ids);
}
