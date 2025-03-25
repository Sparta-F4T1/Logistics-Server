package com.logistic.notification.adapter.out.persistence.repository;

import com.logistic.notification.adapter.out.persistence.SlackMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlackMessageJpaRepository extends JpaRepository<SlackMessageEntity, Long> {
}
